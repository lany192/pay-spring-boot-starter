package com.github.lany192.pay.wechat.service;

import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.util.SignUtils;
import com.github.lany192.pay.wechat.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 交易服务,WxPayService的二次封装
 */
@Slf4j
@Service
public class WxPayTradeService {
    private WxPayService wxPayService;

    @Autowired
    public WxPayTradeService(WxPayService wxPayService) {
        this.wxPayService = wxPayService;
    }

    /**
     * 统一下单
     */
    public Map<String, String> unifiedOrder(WxPayUnifiedOrderRequest request) throws WxPayException {
        /**
         * 30分钟后失效
         */
        Date startDate = new Date();
        request.setTimeStart(TimeUtils.getTimeStart(startDate));
        request.setTimeExpire(TimeUtils.getTimeExpire(startDate, 30));

        WxPayUnifiedOrderResult unifiedOrderResult = wxPayService.unifiedOrder(request);
        String prepayId = unifiedOrderResult.getPrepayId();
        if (StringUtils.isBlank(prepayId)) {
            throw new RuntimeException(String.format("无法获取prepay id，错误代码： '%s'，信息：%s。", unifiedOrderResult.getErrCode(), unifiedOrderResult.getErrCodeDes()));
        } else {
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
            String nonceStr = UUID.randomUUID().toString().replace("-", "");
            WxPayConfig config = wxPayService.getConfig();
            Map<String, String> payInfo = new HashMap<>();
            if (WxPayConstants.TradeType.NATIVE.equals(request.getTradeType())) {
                payInfo.put("codeUrl", unifiedOrderResult.getCodeURL());
            } else if (WxPayConstants.TradeType.APP.equals(request.getTradeType())) {
                Map<String, String> configMap = new HashMap<>();
                configMap.put("prepayid", prepayId);
                configMap.put("partnerid", config.getMchId());
                configMap.put("package", "Sign=WXPay");
                configMap.put("timestamp", timestamp);
                configMap.put("noncestr", nonceStr);
                configMap.put("appid", config.getAppId());

                payInfo.put("sign", SignUtils.createSign(configMap, request.getSignType(), config.getMchKey(), null));
                payInfo.put("prepayId", prepayId);
                payInfo.put("partnerId", config.getMchId());
                payInfo.put("appId", config.getAppId());
                payInfo.put("packageValue", "Sign=WXPay");
                payInfo.put("timeStamp", timestamp);
                payInfo.put("nonceStr", nonceStr);
            } else if (WxPayConstants.TradeType.JSAPI.equals(request.getTradeType())) {
                payInfo.put("appId", unifiedOrderResult.getAppid());
                payInfo.put("timeStamp", timestamp);
                payInfo.put("nonceStr", nonceStr);
                payInfo.put("package", "prepay_id=" + prepayId);
                payInfo.put("signType", request.getSignType());
                payInfo.put("paySign", SignUtils.createSign(payInfo, request.getSignType(), config.getMchKey(), null));
            }
            return payInfo;
        }
    }
}

