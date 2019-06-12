package com.github.lany192.pay.alipay.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePayResponse;
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
     * 支付宝支付
     *
     * @param bizModel AlipayTradePayModel
     * @return AlipayTradePayResponse
     */
    public AlipayTradeAppPayResponse tradePay(AlipayTradeAppPayModel bizModel) throws AlipayApiException {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setBizModel(bizModel); //设置业务参数
        request.setNotifyUrl(properties.getNotifyUrl());
        return alipayClient.execute(request);
    }


//
//    /**
//     * 交易查询
//     *
//     * @param outTradeNo
//     * @return
//     */
//    public AlipayTradeQueryResponse queryByOutTradeNo(String outTradeNo) throws AlipayApiException {
//        AlipayTradeQueryModel request = new AlipayTradeQueryModel();
//        request.setOutTradeNo(outTradeNo);
//        return query(request);
//    }
//
//    /**
//     * 交易查询
//     *
//     * @param tradeNo
//     * @return
//     */
//    public AlipayTradeQueryResponse queryByTradeNo(String tradeNo) throws AlipayApiException {
//        AlipayTradeQueryModel request = new AlipayTradeQueryModel();
//        request.setTradeNo(tradeNo);
//        return query(request);
//    }
//
//    /**
//     * 转账查询
//     *
//     * @param outBizNo 商户转账唯一订单号
//     * @return
//     */
//    public AlipayFundTransOrderQueryResponse queryByOutBizNo(String outBizNo) throws AlipayApiException {
//        AlipayFundTransOrderQueryModel request = new AlipayFundTransOrderQueryModel();
//        request.setOutBizNo(outBizNo);
//        return query(request);
//    }
//
//    /**
//     * 转账查询
//     *
//     * @param orderId 支付宝转账单据号
//     * @return
//     */
//    public AlipayFundTransOrderQueryResponse queryByOrderId(String orderId) throws AlipayApiException {
//        AlipayFundTransOrderQueryModel request = new AlipayFundTransOrderQueryModel();
//        request.setOrderId(orderId);
//        return query(request);
//    }
//
//    /**
//     * 交易查询
//     *
//     * @param tradeQueryModel
//     * @return
//     */
//    private AlipayTradeQueryResponse query(AlipayTradeQueryModel tradeQueryModel) throws AlipayApiException {
//        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
//        request.setBizModel(tradeQueryModel);
//        return alipayClient.execute(request);
//    }
//
//    /**
//     * 转账查询
//     *
//     * @param alipayFundTransOrderQueryModel
//     * @return
//     */
//    private AlipayFundTransOrderQueryResponse query(AlipayFundTransOrderQueryModel alipayFundTransOrderQueryModel) throws AlipayApiException {
//        AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
//        request.setBizModel(alipayFundTransOrderQueryModel);
//        return alipayClient.execute(request);
//    }
//
//    public AlipayTradeCancelResponse cancelByOutTradeNo(String outTradeNo) {
//
//        AlipayTradeCancelModel request = new AlipayTradeCancelModel();
//        request.setOutTradeNo(outTradeNo);
//        return cancel(request);
//    }
//
//    public AlipayTradeCancelResponse cancelByTradeNo(String tradeNo) {
//        AlipayTradeCancelModel request = new AlipayTradeCancelModel();
//        request.setTradeNo(tradeNo);
//        return cancel(request);
//    }
//
//    public AlipayTradeCancelResponse cancel(AlipayTradeCancelModel tradeCancel) throws AlipayApiException {
//        AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
//        request.setBizModel(tradeCancel);
//        return alipayClient.execute(request);
//    }
//
//    public AlipayTradeRefundResponse refundByOutTradeNo(String outTradeNo, String refundAmount) {
//
//        AlipayTradeRefundModel request = new AlipayTradeRefundModel();
//        request.setOutTradeNo(outTradeNo);
//        request.setRefundAmount(refundAmount);
//        return refund(request);
//    }
//
//    public AlipayTradeRefundResponse refundByTradeNo(String tradeNo, String refundAmount) {
//
//        AlipayTradeRefundModel request = new AlipayTradeRefundModel();
//        request.setTradeNo(tradeNo);
//        request.setRefundAmount(refundAmount);
//        return refund(request);
//    }
//
//    public AlipayTradeRefundResponse refund(AlipayTradeRefundModel tradeRefund) {
//
//        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
//
//        request.setBizModel(tradeRefund);
//        AlipayTradeRefundResponse response = null;
//        try {
//            response = alipayClient.execute(request);
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    /**
//     * 下载账单
//     *
//     * @param billDownloadurlQuery
//     * @return AlipayDataDataserviceBillDownloadurlQueryResponse
//     */
//    public AlipayDataDataserviceBillDownloadurlQueryResponse downloadBill(
//            AlipayDataDataserviceBillDownloadurlQueryModel billDownloadurlQuery) {
//        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
//        request.setBizModel(billDownloadurlQuery);
//        AlipayDataDataserviceBillDownloadurlQueryResponse response = null;
//        try {
//            response = alipayClient.execute(request);
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    /**
//     * 支付宝转账到其他账户
//     *
//     * @param transfer AlipayFundTransToAccountTransfer
//     * @return 请求的响应
//     */
//    public AlipayFundTransToaccountTransferResponse transferToAccount(AlipayFundTransToaccountTransferModel transfer) throws AlipayApiException {
//        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
//        request.setBizModel(transfer);
//        return alipayClient.execute(request);
//    }
//
    /**
     * App支付
     *
     * @param request
     * @return
     */
    public String appPay(AlipayTradeAppPayRequest request) throws AlipayApiException {
        //这里和普通的接口调用不同，使用的是sdkExecute
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
        log.info(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
        return response.getBody();
    }
//
//    public MonitorHeartbeatSynResponse sendHeartbeat(MonitorHeartbeatSynRequest request) throws AlipayApiException {
//        return alipayClient.execute(request);
//    }
}