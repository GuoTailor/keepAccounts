package com.gyh.keepaccounts.model

import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * ka_bill
 * @author GYH
 * @apiDefine Bill
 * @apiParam {Integer} id id
 * @apiParam {Integer} userId 用户id
 * @apiParam {String} type 车型
 * @apiParam {String} specification 规格
 * @apiParam {Integer} amount 数量
 * @apiParam {Decimal} price 价格
 * @apiParam {String} remark 备注
 * @apiParam {Decimal} payment 已付款金额
 * @apiParam {String} paymentType 付款类型 wx：微信；zfb：支付宝；rmb：现金；wzf：未支付
 * @apiParam {Date} [createTime] 注册日期
 *
 */
open class Bill(
    var id: Int? = null,

    /**
     * 客户id
     */
    var userId: Int? = null,

    /**
     * 车型
     */
    var type: String? = null,

    /**
     * 规格
     */
    var specification: String? = null,

    /**
     * 数量
     */
    var amount: Int? = null,

    /**
     * 价格
     */
    var price: BigDecimal? = null,

    /**
     * 备注
     */
    var remark: String? = null,

    /**
     * 已付款金额
     */
    var payment: BigDecimal? = null,

    /**
     * 付款类型 wx：微信；zfb：支付宝；rmb：现金；wzf：未支付
     */
    var paymentType: String? = null,

    /**
     * 创建时间
     */
    var createTime: LocalDateTime? = null,
) {
    fun checkPaymentType(): Boolean {
        return (paymentType == "wx" || paymentType == "zfb" || paymentType == "rmb" || paymentType == "wzf")
    }
}