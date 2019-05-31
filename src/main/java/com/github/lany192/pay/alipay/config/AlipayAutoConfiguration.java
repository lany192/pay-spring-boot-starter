package com.github.lany192.pay.alipay.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.github.lany192.pay.alipay.service.AlipayAuthService;
import com.github.lany192.pay.alipay.service.AlipayTradeService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lany
 */
@Configuration
@EnableConfigurationProperties(AlipayProperties.class)
@ConfigurationProperties(prefix = "pay.alipay")
public class AlipayAutoConfiguration {

    @Autowired
    private AlipayProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public AlipayClient alipayClient() {
        byte[] privateKeyBytes = properties.getPrivateKey().getEncoded();
        byte[] publicKeyBytes = properties.getAlipayPublicKey().getEncoded();
        return new DefaultAlipayClient(properties.getHost() + "/gateway.do",
                properties.getAppId(),
                Base64.encodeBase64String(privateKeyBytes),
                AlipayConstants.FORMAT_JSON,
                AlipayConstants.CHARSET_UTF8,
                Base64.encodeBase64String(publicKeyBytes),
                AlipayConstants.SIGN_TYPE_RSA2);
    }

    @Bean
    @ConditionalOnMissingBean
    public AlipayTradeService alipayTradeService() {
        return new AlipayTradeService(properties, alipayClient());
    }

    @Bean
    @ConditionalOnMissingBean
    public AlipayAuthService alipayAuthService() {
        return new AlipayAuthService(properties, alipayClient());
    }
}
