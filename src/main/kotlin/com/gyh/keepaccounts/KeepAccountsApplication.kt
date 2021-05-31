package com.gyh.keepaccounts

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@MapperScan("com.gyh.keepaccounts.mapper")
@EnableTransactionManagement
class KeepAccountsApplication

fun main(args: Array<String>) {
    runApplication<KeepAccountsApplication>(*args)
}
