package com.gyh.keepaccounts.mapper

import com.gyh.keepaccounts.model.Bill

interface BillMapper {
    fun deleteByPrimaryKey(id: Int): Int
    fun insert(record: Bill): Int
    fun insertSelective(record: Bill): Int
    fun selectByPrimaryKey(id: Int): Bill?
    fun updateByPrimaryKeySelective(record: Bill): Int
    fun updateByPrimaryKey(record: Bill): Int
}