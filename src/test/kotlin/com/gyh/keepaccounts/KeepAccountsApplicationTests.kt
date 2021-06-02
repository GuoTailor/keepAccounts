package com.gyh.keepaccounts

import com.gyh.keepaccounts.service.OSSClient
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

//@SpringBootTest
class KeepAccountsApplicationTests {

    @Test
    fun contextLoads() {
        //OSSClient.deleteFile(1)
        val list = listOf("12", "23", "45")
        println(list.joinToString(separator = " ") { it })
    }


}
