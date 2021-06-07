package com.gyh.keepaccounts.service

import com.github.pagehelper.PageHelper
import com.gyh.keepaccounts.common.firstDay
import com.gyh.keepaccounts.common.lastDay
import com.gyh.keepaccounts.common.toLocalDateTime
import com.gyh.keepaccounts.mapper.BillMapper
import com.gyh.keepaccounts.mapper.PurchaseMapper
import com.gyh.keepaccounts.model.Bill
import com.gyh.keepaccounts.model.PageView
import com.gyh.keepaccounts.model.view.BillRequestInfo
import com.gyh.keepaccounts.model.view.BillResponseInfo
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalTime
import javax.annotation.Resource

/**
 * Created by gyh on 2021/5/31
 */
@Service
class BillService {
    @Resource
    lateinit var billMapper: BillMapper

    @Resource
    lateinit var purchaseMapper: PurchaseMapper

    fun createBill(bill: BillRequestInfo): Int {
        if (!bill.checkPaymentType()) error("付款类型应为wx：微信；zfb：支付宝；rmb：现金；wzf：未支付")
        return billMapper.batchInsert(bill.createBill())
    }

    fun updateBill(bill: Bill): Int {
        if (!bill.checkPaymentType()) error("付款类型应为wx：微信；zfb：支付宝；rmb：现金；wzf：未支付")
        return billMapper.updateByPrimaryKeySelective(bill)
    }

    fun getAllBill(page: Int, size: Int, userId: Int?): PageView<BillResponseInfo> {
        PageHelper.startPage<Any>(page, size)
        return PageView.build(billMapper.selectAll(userId))
    }

    fun getBill(id: Int): BillResponseInfo {
        return billMapper.selectByPrimaryKey(id) ?: error("不存在该订单：$id")
    }

    fun deleteBill(id: Int): Int {
        return billMapper.deleteByPrimaryKey(id)
    }

    /**
     * 统计销售额
     */
    fun countSalesVolume(): MutableMap<String, Any> {
        val day = billMapper.countSalesVolume(LocalTime.MIN.toLocalDateTime()) ?: BigDecimal.ZERO
        val month = billMapper.countSalesVolume(firstDay()) ?: BigDecimal.ZERO
        val nonPayment = billMapper.countNonPayment() ?: BigDecimal.ZERO
        val statistics = purchaseMapper.statistics()
        statistics["accountPaid"] = statistics["accountPaid"] ?: BigDecimal.ZERO
        statistics["arrearage"] = statistics["arrearage"] ?: BigDecimal.ZERO
        statistics.putAll(mapOf("daySell" to day, "monthSell" to month, "nonPayment" to nonPayment))
        return statistics
    }

    /**
     * 统计未收款记录
     */
    fun findDebt(page: Int, size: Int, field: String, order: String): PageView<BillResponseInfo> {
        val startPage = PageHelper.startPage<BillResponseInfo>(page, size)
        startPage.setOrderBy<BillResponseInfo>("$field $order")
        return PageView.build(billMapper.findDebt())
    }

    fun findTodayBill(page: Int, size: Int): PageView<BillResponseInfo> {
        PageHelper.startPage<BillResponseInfo>(page, size)
        return PageView.build(billMapper.findBillByCreateTime(LocalTime.MIN.toLocalDateTime(), LocalTime.MAX.toLocalDateTime()))
    }

    fun findCurrentMonthBill(page: Int, size: Int): PageView<BillResponseInfo> {
        PageHelper.startPage<BillResponseInfo>(page, size)
        return PageView.build(billMapper.findBillByCreateTime(firstDay(), lastDay()))
    }

}