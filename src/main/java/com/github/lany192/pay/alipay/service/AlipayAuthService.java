package com.github.lany192.pay.alipay.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserUserinfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserUserinfoShareResponse;
import com.github.lany192.pay.alipay.config.AlipayProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝用户认证相关Service
 *
 * @author Administrator
 */
@Slf4j
@Service
public class AlipayAuthService {
    private final AlipayProperties properties;
    private final AlipayClient alipayClient;

    @Autowired
    public AlipayAuthService(AlipayProperties properties, AlipayClient alipayClient) {
        this.properties = properties;
        this.alipayClient = alipayClient;
    }

    public AlipayProperties getProperties() {
        return properties;
    }

    /**
     * 根据authCode获得AccessToken
     *
     * @param authCode     authCode
     * @param alipayUserId alipayUserId
     * @return AccessToken
     */
    public String userAuth(String authCode, String alipayUserId) throws AlipayApiException {
        //创建API对应的request类
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setGrantType("authorization_code");
        request.setCode(authCode);
        //通过alipayClient调用API，获得对应的response类
        AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
        //根据response中的结果继续业务逻辑处理
        if (response == null) {
            return "";
        }
        if (!response.getUserId().equals(alipayUserId)) {
            return "";
        }
        return response.getAccessToken();
    }

    /**
     * 使用accessToken获取用户信息
     *
     * @param accessToken token
     * @return AlipayUserUserinfoShareResponse
     */
    public AlipayUserUserinfoShareResponse getAlipayUserInfo(String accessToken) throws AlipayApiException {
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.user.userinfo.share
        AlipayUserUserinfoShareRequest request = new AlipayUserUserinfoShareRequest();
        //授权类接口执行API调用时需要带上accessToken
        return alipayClient.execute(request, accessToken);
    }

    /**
     * 支付宝授权加签
     *
     * @return 签名后字符串
     */
    public String rsaSign() throws AlipayApiException {
        Map<String, String> params = new HashMap<>();
        // 服务接口名称， 固定值
        params.put("apiname", "com.alipay.account.auth");
        params.put("method", "alipay.open.auth.sdk.code.get");
        params.put("app_id", properties.getAppId());
        // 商户类型标识， 固定值
        params.put("app_name", "mc");
        // 商户签约拿到的pid，如：2088102123816631
        params.put("pid", properties.getPid());
        // 业务类型， 固定值
        params.put("biz_type", "openservice");
        // 产品码， 固定值
        params.put("product_id", "APP_FAST_LOGIN");
        // 授权范围， 固定值
        params.put("scope", "kuaijie");
        // 商户唯一标识，如：kkkkk091125
        params.put("target_id", new Date().toString());
        // 授权类型， 固定值
        params.put("auth_type", "AUTHACCOUNT");
        // 签名类型
        params.put("sign_type", AlipayConstants.SIGN_TYPE_RSA2);

        byte[] privateKeyBytes = properties.getPrivateKey().getEncoded();
        String sign = AlipaySignature.rsaSign(AlipaySignature.getSignContent(params), Base64.encodeBase64String(privateKeyBytes),
                AlipayConstants.CHARSET_UTF8, AlipayConstants.SIGN_TYPE_RSA2);
        try {
            return AlipaySignature.getSignContent(params) + "&sign=" + URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean rsaCheckV1(Map<String, String> params) {
        byte[] publicKey = properties.getPublicKey().getEncoded();
        try {
            return AlipaySignature.rsaCheckV1(params, Base64.encodeBase64String(publicKey),
                    AlipayConstants.CHARSET_UTF8, AlipayConstants.SIGN_TYPE_RSA2);
        } catch (AlipayApiException e) {
            log.error("异步返回结果验签失败", e);
        }
        return false;
    }
}

