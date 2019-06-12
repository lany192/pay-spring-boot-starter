package com.github.lany192.pay.alipay.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.github.lany192.pay.alipay.config.AlipayProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    public boolean rsaCheckV1(Map<String, String> params) throws AlipayApiException {
        return AlipaySignature.rsaCheckV1(params, properties.getPublicKey(),
                AlipayConstants.CHARSET_UTF8, AlipayConstants.SIGN_TYPE_RSA2);
    }


    /**
     * 支付宝支付
     *
     * @param bizModel 业务参数
     */
    public AlipayTradeAppPayResponse tradePay(AlipayTradeAppPayModel bizModel) throws AlipayApiException {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setBizModel(bizModel); //设置业务参数
        request.setNotifyUrl(properties.getNotifyUrl());
        //这里和普通的接口调用不同，使用的是sdkExecute
        return alipayClient.sdkExecute(request);
    }

    /**
     * 交易查询
     *
     * @param outTradeNo
     * @return
     */
    public AlipayTradeQueryResponse queryByOutTradeNo(String outTradeNo) throws AlipayApiException {
        AlipayTradeQueryModel request = new AlipayTradeQueryModel();
        request.setOutTradeNo(outTradeNo);
        return query(request);
    }

    /**
     * 交易查询
     *
     * @param tradeNo
     * @return
     */
    public AlipayTradeQueryResponse queryByTradeNo(String tradeNo) throws AlipayApiException {
        AlipayTradeQueryModel request = new AlipayTradeQueryModel();
        request.setTradeNo(tradeNo);
        return query(request);
    }

    /**
     * 转账查询
     *
     * @param outBizNo 商户转账唯一订单号
     * @return
     */
    public AlipayFundTransOrderQueryResponse queryByOutBizNo(String outBizNo) throws AlipayApiException {
        AlipayFundTransOrderQueryModel request = new AlipayFundTransOrderQueryModel();
        request.setOutBizNo(outBizNo);
        return query(request);
    }

    /**
     * 转账查询
     *
     * @param orderId 支付宝转账单据号
     * @return
     */
    public AlipayFundTransOrderQueryResponse queryByOrderId(String orderId) throws AlipayApiException {
        AlipayFundTransOrderQueryModel request = new AlipayFundTransOrderQueryModel();
        request.setOrderId(orderId);
        return query(request);
    }

    /**
     * 交易查询
     *
     * @param tradeQueryModel
     * @return
     */
    private AlipayTradeQueryResponse query(AlipayTradeQueryModel tradeQueryModel) throws AlipayApiException {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizModel(tradeQueryModel);
        return alipayClient.execute(request);
    }

    /**
     * 转账查询
     */
    private AlipayFundTransOrderQueryResponse query(AlipayFundTransOrderQueryModel alipayFundTransOrderQueryModel) throws AlipayApiException {
        AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
        request.setBizModel(alipayFundTransOrderQueryModel);
        return alipayClient.execute(request);
    }

    public AlipayTradeCancelResponse cancelByOutTradeNo(String outTradeNo) throws AlipayApiException {
        AlipayTradeCancelModel model = new AlipayTradeCancelModel();
        model.setOutTradeNo(outTradeNo);
        AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
        request.setBizModel(model);
        return alipayClient.execute(request);
    }

    public AlipayTradeCancelResponse cancelByTradeNo(String tradeNo) throws AlipayApiException {
        AlipayTradeCancelModel model = new AlipayTradeCancelModel();
        model.setTradeNo(tradeNo);
        AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
        request.setBizModel(model);
        return alipayClient.execute(request);
    }

    public AlipayTradeRefundResponse refundByOutTradeNo(String outTradeNo, String refundAmount) throws AlipayApiException {
        AlipayTradeRefundModel request = new AlipayTradeRefundModel();
        request.setOutTradeNo(outTradeNo);
        request.setRefundAmount(refundAmount);
        return refund(request);
    }

    public AlipayTradeRefundResponse refundByTradeNo(String tradeNo, String refundAmount) throws AlipayApiException {
        AlipayTradeRefundModel request = new AlipayTradeRefundModel();
        request.setTradeNo(tradeNo);
        request.setRefundAmount(refundAmount);
        return refund(request);
    }

    public AlipayTradeRefundResponse refund(AlipayTradeRefundModel tradeRefund) throws AlipayApiException {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizModel(tradeRefund);
        return alipayClient.execute(request);
    }

    /**
     * 下载账单
     */
    public AlipayDataDataserviceBillDownloadurlQueryResponse downloadBill(
            AlipayDataDataserviceBillDownloadurlQueryModel billDownloadurlQuery) throws AlipayApiException {
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        request.setBizModel(billDownloadurlQuery);
        return alipayClient.execute(request);
    }

    /**
     * 支付宝转账到其他账户
     */
    public AlipayFundTransToaccountTransferResponse transferToAccount(AlipayFundTransToaccountTransferModel transfer) throws AlipayApiException {
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        request.setBizModel(transfer);
        return alipayClient.execute(request);
    }
}