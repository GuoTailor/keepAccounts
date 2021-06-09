package com.gyh.keepaccounts

import com.gyh.keepaccounts.service.BillService
import com.gyh.keepaccounts.service.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class KeepAccountsApplicationTests {
    @Autowired
    lateinit var billService: BillService

    @Autowired
    lateinit var userService: UserService

    @Test
    fun contextLoads() {
        println(billService.findDetail(1, 30, 3, false))
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
