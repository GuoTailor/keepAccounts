package com.gyh.keepaccounts

import com.gyh.keepaccounts.common.firstDay
import com.gyh.keepaccounts.common.lastDay
import com.gyh.keepaccounts.service.BillService
import com.gyh.keepaccounts.service.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class KeepAccountsApplicationTests {
    @Autowired
    lateinit var billService: BillService

    @Autowired
    lateinit var userService: UserService

    @Test
    fun contextLoads() {
        billService.outExel(firstDay(), lastDay())
    }

    @Test
    fun nmak() {
        val s = ""
        val split = s.split(" ")
        println(split)
        println(split.size)
        println(split.isEmpty())
        split.forEach { println(it) }
        println(s.isNotBlank())
    }


}
