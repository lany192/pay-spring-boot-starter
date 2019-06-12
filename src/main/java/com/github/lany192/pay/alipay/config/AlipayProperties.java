package com.github.lany192.pay.alipay.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "pay.alipay")
public class AlipayProperties {
    private String host = "https://openapi.alipay.com";
    /**
     * 支付宝应用id
     */
    private String appId;
    /**
     * 签约的支付宝账号对应的支付宝唯一用户号
     */
    private String pid;

    /**
     * 应用私钥
     */
    private String privateKey;

    /**
     * 支付宝公钥
     */
    private String publicKey;
    /**
     * 支付异步回调通知URL
     */
    private String notifyUrl;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }
}
