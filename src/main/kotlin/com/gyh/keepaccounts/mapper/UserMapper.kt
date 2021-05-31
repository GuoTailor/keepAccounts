package com.gyh.keepaccounts.mapper

import com.gyh.keepaccounts.model.User

interface UserMapper {
    fun deleteByPrimaryKey(id: Int): Int
    fun insert(record: User): Int
    fun insertSelective(record: User): Int
    fun selectByPrimaryKey(id: Int): User?
    fun loadUserByUsername(s: String): User?
    fun updateByPrimaryKeySelective(record: User): Int
    fun updateByPrimaryKey(record: User): Int
}