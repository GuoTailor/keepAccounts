package com.gyh.keepaccounts.service

import com.gyh.keepaccounts.mapper.BillMapper
import org.springframework.stereotype.Service
import javax.annotation.Resource

/**
 * Created by gyh on 2021/5/31
 */
@Service
class BillService {
    @Resource
    lateinit var billMapper: BillMapper


}