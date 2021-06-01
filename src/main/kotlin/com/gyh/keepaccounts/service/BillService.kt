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
        return billMapper.batchInsert(bill.createBill())
    }

    fun updateBill(bill: Bill): Int {
        return billMapper.updateByPrimaryKeySelective(bill)
    }

    fun getAllBill(page: Int, size: Int, userId: Int?): PageView<BillResponseInfo> {
        PageHelper.startPage<Any>(page, size)
        return PageView.build(billMapper.selectAll(userId))
    }
}