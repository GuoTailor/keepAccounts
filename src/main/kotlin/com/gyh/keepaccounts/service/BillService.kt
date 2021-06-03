package com.gyh.keepaccounts.service

import com.github.pagehelper.PageHelper
import com.gyh.keepaccounts.mapper.BillMapper
import com.gyh.keepaccounts.model.Bill
import com.gyh.keepaccounts.model.PageView
import com.gyh.keepaccounts.model.view.BillRequestInfo
import com.gyh.keepaccounts.model.view.BillResponseInfo
import org.springframework.stereotype.Service
import javax.annotation.Resource

/**
 * Created by gyh on 2021/5/31
 */
@Service
class BillService {
    @Resource
    lateinit var billMapper: BillMapper

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

}