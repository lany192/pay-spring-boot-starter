package com.github.lany192.pay.wechat.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WxPayDetail {

    /**
     * 必填 32 商品的编号
     */
    private String goodsId;

    /**
     * 可选 32 微信支付定义的统一商品编号
     */
    private String wxpayGoodsId;

    /**
     * 必填 256 商品名称
     */
    private String name;

    /**
     * 必填 商品数量
     */
    private Integer number;

    /**
     * 必填 商品单价，单位为分
     */
    private Integer price;
}

