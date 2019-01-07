package com.github.lany192.pay.wechat.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.github.lany192.pay.wechat.service.WechatTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 */
@Configuration
@ConditionalOnClass(WxPayService.class)
@EnableConfigurationProperties(WxPayProperties.class)
@ConditionalOnProperty(prefix = "wechat.pay", name = {"app-id", "mch-id", "mch-key"})
public class WxPayAutoConfiguration {

    @Autowired
    private WxPayProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public WxPayConfig wxPayConfig() {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(this.properties.getAppId());
        payConfig.setMchId(this.properties.getMchId());
        payConfig.setMchKey(this.properties.getMchKey());
        payConfig.setSubAppId(this.properties.getSubAppId());
        payConfig.setSubMchId(this.properties.getSubMchId());
        payConfig.setKeyPath(this.properties.getKeyPath());
        return payConfig;
    }

    @Bean
    //@ConditionalOnMissingBean
    public WxPayService wxPayService(WxPayConfig payConfig) {
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }

    @Bean
    public WechatTradeService wechatTradeService(WxPayService wxPayService) {
        WechatTradeService wechatTradeService = new WechatTradeService();
        wechatTradeService.setWxPayService(wxPayService);
        return wechatTradeService;
    }
}

