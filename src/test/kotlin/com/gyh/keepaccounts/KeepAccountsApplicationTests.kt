package com.gyh.keepaccounts

import com.gyh.keepaccounts.common.firstDay
import com.gyh.keepaccounts.common.lastDay
import com.gyh.keepaccounts.common.toEpochMilli
import com.gyh.keepaccounts.service.BillService
import com.gyh.keepaccounts.service.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

@SpringBootTest
class KeepAccountsApplicationTests {
    @Autowired
    lateinit var billService: BillService

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun contextLoads() {
        billService.outExel(firstDay(), lastDay())
    }

    @Test
    fun nmak() {
        println(passwordEncoder.encode("admin"))
        println(firstDay().toEpochMilli())
        println(lastDay().toEpochMilli())

    }


}
