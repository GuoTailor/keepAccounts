package com.gyh.keepaccounts.model.view

import com.gyh.keepaccounts.model.Bill
import java.math.BigDecimal

/**
 * Created by gyh on 2021/6/1
 * @apiDefine BillResponseInfo
 * @apiSuccess (返回) {Integer} id id
 * @apiSuccess (返回) {String} username 客户名称
 * @apiSuccess (返回) {Integer} userId 用户id
 * @apiSuccess (返回) {String} type 车型
 * @apiSuccess (返回) {String} specification 规格
 * @apiSuccess (返回) {Integer} amount 数量
 * @apiSuccess (返回) {Decimal} price 价格
 * @apiSuccess (返回) {String} remark 备注
 * @apiSuccess (返回) {Decimal} payment 已付款金额
 * @apiSuccess (返回) {Decimal} debt 欠款
 * @apiSuccess (返回) {String} paymentType 付款类型 wx：微信；zfb：支付宝；rmb：现金；wzf：未支付
 * @apiSuccess (返回) {Date} [createTime] 注册日期
 */
data class BillResponseInfo(val username: String, val debt: BigDecimal): Bill()