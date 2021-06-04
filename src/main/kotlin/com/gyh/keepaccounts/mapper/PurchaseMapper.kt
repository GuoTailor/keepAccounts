package com.gyh.keepaccounts.mapper

import com.gyh.keepaccounts.model.Purchase

interface PurchaseMapper {
    fun deleteByPrimaryKey(id: Int): Int
    fun insert(record: Purchase): Int
    fun insertSelective(record: Purchase): Int
    fun selectByPrimaryKey(id: Int): Purchase?
    fun findAll(): List<Purchase>
    fun statistics(): MutableMap<String, Any>
    fun updateByPrimaryKeySelective(record: Purchase): Int
    fun updateByPrimaryKey(record: Purchase): Int
}