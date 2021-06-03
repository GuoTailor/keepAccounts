package com.gyh.keepaccounts.model

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

/**
 * ka_purchase
 * @author
 * @apiDefine Purchase
 * @apiParam {Integer} id id
 * @apiParam {String} project 支出项目
 * @apiParam {String} counterparty 交易方
 * @apiParam {Decimal} money 金额
 * @apiParam {String} paymentType 付款类型 wx：微信；zfb：支付宝；rmb：现金；wzf：未支付
 * @apiParam {String} remark 备注
 * @apiParam {Date} createTime 创建时间
 */
class Purchase(
    var id: Int? = null,

    /**
     * 支出项目
     */
    var project: String? = null,

    /**
     * 交易方
     */
    var counterparty: String? = null,

    /**
     * 金额
     */
    var money: BigDecimal? = null,

    /**
     * 付款类型 wx：微信；zfb：支付宝；rmb：现金；wzf：未支付
     */
    var paymentType: String? = null,

    /**
     * 备注
     */
    var remark: String? = null,

    /**
     * 创建时间
     */
    var createTime: LocalDateTime? = null,
) {
    fun checkPaymentType(): Boolean {
        return (paymentType == "wx" || paymentType == "zfb" || paymentType == "rmb" || paymentType == "wzf")
    }
}