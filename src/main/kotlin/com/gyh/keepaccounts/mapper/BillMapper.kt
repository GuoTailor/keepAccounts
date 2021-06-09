package com.gyh.keepaccounts.mapper

import com.gyh.keepaccounts.model.Bill
import com.gyh.keepaccounts.model.view.BillResponseInfo
import java.math.BigDecimal
import java.time.LocalDateTime

interface BillMapper {
    fun deleteByPrimaryKey(id: Int): Int
    fun insert(record: Bill): Int
    fun insertSelective(record: Bill): Int
    fun batchInsert(list: List<Bill>): Int
    fun selectByPrimaryKey(id: Int): BillResponseInfo?
    fun selectAll(userId: Int?): List<BillResponseInfo>
    fun countSalesVolume(startTime: LocalDateTime, endTime: LocalDateTime): BigDecimal?
    fun countNonPayment(): BigDecimal?
    fun findDebt(): List<BillResponseInfo>
    fun findBillByCreateTime(startTime: LocalDateTime, endTime: LocalDateTime): List<BillResponseInfo>
    fun countConsume(userId: Int): MutableMap<String, BigDecimal>
    fun findDetail(userId: Int, isDebt: String): List<BillResponseInfo>
    fun updateByPrimaryKeySelective(record: Bill): Int
    fun updateByPrimaryKey(record: Bill): Int
    fun batchUpdatePayment(ids: List<Int>, paymentType: String): Int
}