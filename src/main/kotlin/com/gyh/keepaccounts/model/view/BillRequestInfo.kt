package com.gyh.keepaccounts.model.view

import com.gyh.keepaccounts.model.Bill
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * Created by gyh on 2021/6/1
 * @apiDefine BillRequestInfo
 * @apiParam {Integer} id id
 * @apiParam {Integer} userId 用户id
 * @apiParam {String} type 车型
 * @apiParam {String} specification 规格
 * @apiParam {Integer} amount 数量
 * @apiParam {Decimal} price 价格
 * @apiParam {String} remark 备注
 * @apiParam {String} paymentType 付款类型 wx：微信；zfb：支付宝；rmb：现金；wzf：未支付
 * @apiParam {Date} [createTime] 注册日期
 */
class BillRequestInfo {
    /**
     * 客户id
     */
    var userId: Int? = null
    var billList: List<BillList> = listOf()

    /**
     * 备注
     */
    var remark: String? = null

    /**
     * 付款类型 wx：微信；zfb：支付宝；rmb：现金；wzf：未支付
     */
    var paymentType: String? = null

    /**
     * 创建时间
     */
    var createTime: LocalDateTime? = null

    data class BillList(
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
    )

    fun checkPaymentType(): Boolean {
        return (paymentType == "wx" || paymentType == "zfb" || paymentType == "rmb" || paymentType == "wzf")
    }

    fun createBill(): List<Bill> {
        return billList.map {
            Bill(
                userId = userId,
                remark = remark,
                payment = if (paymentType == "wzf") BigDecimal.ZERO else it.price,
                paymentType = paymentType,
                createTime = createTime,
                type = it.type,
                specification = it.specification,
                amount = it.amount,
                price = it.price
            )
        }
    }
}