package com.gyh.keepaccounts.mapper

import com.gyh.keepaccounts.model.Bill
import com.gyh.keepaccounts.model.view.BillResponseInfo

interface BillMapper {
    fun deleteByPrimaryKey(id: Int): Int
    fun insert(record: Bill): Int
    fun insertSelective(record: Bill): Int
    fun batchInsert(list: List<Bill>): Int
    fun selectByPrimaryKey(id: Int): BillResponseInfo?
    fun selectAll(userId: Int?): List<BillResponseInfo>
    fun updateByPrimaryKeySelective(record: Bill): Int
    fun updateByPrimaryKey(record: Bill): Int
}