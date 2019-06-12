package com.github.lany192.pay.wechat.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.github.lany192.pay.wechat.service.WxPayTradeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信支付参数配置
 */
@Configuration
@ConditionalOnClass(WxPayService.class)
@EnableConfigurationProperties(WxPayProperties.class)
public class WxPayConfiguration {
    private final WxPayProperties properties;

    @Autowired
    public WxPayConfiguration(WxPayProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxPayConfig config() {
        WxPayConfig config = new WxPayConfig();
        config.setAppId(StringUtils.trimToNull(properties.getAppId()));
        config.setMchId(StringUtils.trimToNull(properties.getMchId()));
        config.setMchKey(StringUtils.trimToNull(properties.getMchKey()));
        config.setSubAppId(StringUtils.trimToNull(properties.getSubAppId()));
        config.setSubMchId(StringUtils.trimToNull(properties.getSubMchId()));
        config.setKeyPath(StringUtils.trimToNull(properties.getKeyPath()));
        config.setNotifyUrl(StringUtils.trimToNull(properties.getNotifyUrl()));
        return config;
    }

    @Bean
    public WxPayService wxPayService(WxPayConfig payConfig) {
        WxPayService service = new WxPayServiceImpl();
        service.setConfig(payConfig);
        return service;
    }

    @Bean
    public WxPayTradeService tradeService(WxPayService wxPayService) {
        WxPayTradeService service = new WxPayTradeService();
        service.setWxPayService(wxPayService);
        return service;
    }
}

