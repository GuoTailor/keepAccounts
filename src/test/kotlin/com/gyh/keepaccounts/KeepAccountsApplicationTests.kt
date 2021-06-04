package com.gyh.keepaccounts

import com.gyh.keepaccounts.service.BillService
import com.gyh.keepaccounts.service.OSSClient
import com.gyh.keepaccounts.service.PurchaseService
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
        println(userService.findConsume(1, 30, "price", "desc"))
    }


}
