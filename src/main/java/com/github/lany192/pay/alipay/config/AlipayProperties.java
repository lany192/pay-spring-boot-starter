package com.github.lany192.pay.alipay.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.PrivateKey;
import java.security.PublicKey;

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
    private PrivateKey privateKey;

    /**
     * 支付宝公钥
     */
    private PublicKey publicKey;
    /**
     * 支付异步回调通知URL
     */
    private String notifyUrl;

    public void setPrivateKey(Resource privateKey) throws IOException {
        try (PEMParser parser = new PEMParser(new InputStreamReader(privateKey.getInputStream()))) {
            PEMKeyPair pemKeyPair = (PEMKeyPair) parser.readObject();
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            this.privateKey = converter.getPrivateKey(pemKeyPair.getPrivateKeyInfo());
        }
    }

    public void setPublicKey(Resource publicKey) throws IOException {
        try (PEMParser parser = new PEMParser(new InputStreamReader(publicKey.getInputStream()))) {
            SubjectPublicKeyInfo subjectPublicKeyInfo = (SubjectPublicKeyInfo) parser.readObject();
            JcaPEMKeyConverter keyConverter = new JcaPEMKeyConverter();
            this.publicKey = keyConverter.getPublicKey(subjectPublicKeyInfo);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }
}
