package com.github.lany192.pay.wechat.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Lany
 * @date 2018/3/29
 */
@Setter
@Getter
public class WxPayGoodsDetail {

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
    private String goodsName;

    /**
     * 必填 商品数量
     */
    private Integer goodsNum;

    /**
     * 必填 商品单价，单位为分
     */
    private Integer price;
}

