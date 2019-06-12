package com.github.lany192.pay.alipay.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.github.lany192.pay.alipay.config.AlipayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 支付宝交易Service
 */
@Slf4j
@Service
public class AlipayTradeService {
    private final AlipayProperties properties;
    private final AlipayClient alipayClient;

    @Autowired
    public AlipayTradeService(AlipayProperties properties, AlipayClient client) {
        this.properties = properties;
        this.alipayClient = client;
    }

    public AlipayProperties getProperties() {
        return properties;
    }

    /**
     * APP支付
     *
     * @param bizModel 业务参数
     * @return 返回结果
     * @throws AlipayApiException
     */
    public AlipayTradeAppPayResponse tradeAppPay(AlipayTradeAppPayModel bizModel) throws AlipayApiException {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setBizModel(bizModel);
        request.setNotifyUrl(properties.getNotifyUrl());
        //这里和普通的接口调用不同，使用的是sdkExecute
        return alipayClient.sdkExecute(request);
    }
}