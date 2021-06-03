package com.gyh.keepaccounts.service

import com.github.pagehelper.PageHelper
import com.gyh.keepaccounts.mapper.PurchaseMapper
import com.gyh.keepaccounts.model.PageView
import com.gyh.keepaccounts.model.Purchase
import org.springframework.stereotype.Service
import javax.annotation.Resource

/**
 * Created by GYH on 2021/6/3
 */
@Service
class PurchaseService {
    @Resource
    lateinit var purchaseMapper: PurchaseMapper

    fun createPurchase(purchase: Purchase): Int {
        if (!purchase.checkPaymentType()) error("付款类型应为wx：微信；zfb：支付宝；rmb：现金；wzf：未支付")
        return purchaseMapper.insertSelective(purchase)
    }

    fun findById(id: Int): Purchase? {
        return purchaseMapper.selectByPrimaryKey(id)
    }

    fun findByPage(page: Int, size: Int): PageView<Purchase> {
        PageHelper.startPage<Purchase>(page, size)
        return PageView.build(purchaseMapper.findAll())
    }

    fun updatePurchase(purchase: Purchase): Int {
        if (purchase.paymentType != null && !purchase.checkPaymentType()) error("付款类型应为wx：微信；zfb：支付宝；rmb：现金；wzf：未支付")
        return purchaseMapper.updateByPrimaryKeySelective(purchase)
    }

    fun deletePurchase(id: Int): Int {
        return purchaseMapper.deleteByPrimaryKey(id)
    }
}