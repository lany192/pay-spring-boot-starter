package com.github.lany192.pay.wechat.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "wechat.pay")
public class WxPayProperties {
    /**
     * 设置微信公众号的appid
     */
    private String appId;

    /**
     * 微信支付商户号
     */
    private String mchId;

    /**
     * 微信支付商户密钥
     */
    private String mchKey;

    /**
     * 服务商模式下的子商户公众账号ID
     */
    private String subAppId;

    /**
     * 服务商模式下的子商户号
     */
    private String subMchId;

    /**
     * apiclient_cert.p12的文件的绝对路径
     */
    private String keyPath;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}

