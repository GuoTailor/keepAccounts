package com.gyh.keepaccounts.controller

import com.gyh.keepaccounts.model.PageView
import com.gyh.keepaccounts.model.Purchase
import com.gyh.keepaccounts.model.ResponseInfo
import com.gyh.keepaccounts.service.PurchaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * Created by GYH on 2021/6/3
 */
@RestController
@RequestMapping("/purchase")
class PurchaseController {
    @Autowired
    lateinit var purchaseService: PurchaseService

    /**
     * @api {post} /purchase 添加采购单
     * @apiDescription 添加采购单
     * @apiName createPurchase
     * @apiVersion 0.0.1
     * @apiUse Purchase
     * @apiParamExample {json} 请求示例:
     * {"project": "大灯","counterparty": "张三","money": 1200.89,"paymentType": "wzf","remark": "备注"}
     * @apiSuccessExample {json} 成功返回:
     * {"code": 1,"msg": "成功","data": {}}
     * @apiGroup Purchase
     * @apiPermission user
     */
    @PostMapping
    fun createPurchase(@RequestBody purchase: Purchase): ResponseInfo<Int> {
        return ResponseInfo.ok(purchaseService.createPurchase(purchase))
    }

    /**
     * @api {get} /purchase 根据id查询采购单
     * @apiDescription 根据id查询采购单
     * @apiName findPurchaseById
     * @apiVersion 0.0.1
     * @apiParam {Integer} id 账单id
     * @apiSuccessExample {json} 成功返回:
     * {"code": 1,"msg": "成功","data": {"id": 1,"project": "大灯","counterparty": "张三","money": 1200.89,
     * "paymentType": "wzf","remark": "备注","createTime": 1622711692000}}
     * @apiGroup Purchase
     * @apiPermission user
     */
    @GetMapping
    fun findPurchaseById(@RequestParam id: Int): ResponseInfo<Purchase?> {
        return ResponseInfo.ok(purchaseService.findById(id))
    }

    /**
     * @api {get} /purchase/page 分页查询采购单
     * @apiDescription 分页查询采购单
     * @apiName findPurchaseByPage
     * @apiVersion 0.0.1
     * @apiParam {Integer} [page] 第几页(从一开始)
     * @apiParam {Integer} [size] 每页大小
     * @apiSuccessExample {json} 成功返回:
     * {"code": 1,"msg": "成功","data": {"pageNum": 1,"pageSize": 30,"total": 2,"list": [{"id": 1,"project": "大灯",
     * "counterparty": "张三","money": 1200.89,"paymentType": "wzf","remark": "备注","createTime": 1622711692000},{"id":
     * 2,"project": "大灯","counterparty": "张三","money": 1200.89,"paymentType": "wzf","remark": "备注","createTime":
     * 1622711767000}],"pages": 1}}
     * @apiGroup Purchase
     * @apiPermission user
     */
    @GetMapping("/page")
    fun findPurchaseByPage(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?,
    ): ResponseInfo<PageView<Purchase>> {
        return ResponseInfo.ok(purchaseService.findByPage(page ?: 1, size ?: 30))
    }

    /**
     * @api {put} /purchase 更新采购单
     * @apiDescription 更新采购单
     * @apiName updatePurchase
     * @apiVersion 0.0.1
     * @apiUse Purchase
     * @apiParamExample {json} 请求示例:
     * {"id": 1,"project": "大灯","counterparty": "张三","money": 120.89,"paymentType": "wx","remark": "备注"}
     * @apiSuccessExample {json} 成功返回:
     * {"code": 1,"msg": "成功","data": {}}
     * @apiGroup Purchase
     * @apiPermission user
     */
    @PutMapping
    fun updatePurchase(@RequestBody purchase: Purchase): ResponseInfo<Int> {
        return ResponseInfo.ok(purchaseService.updatePurchase(purchase))
    }

    /**
     * @api {delete} /purchase 根据id删除采购单
     * @apiDescription 根据id删除采购单
     * @apiName deletePurchase
     * @apiVersion 0.0.1
     * @apiParam {Integer} id 账单id
     * @apiSuccessExample {json} 成功返回:
     * {"code": 1,"msg": "成功","data": {}}
     * @apiGroup Purchase
     * @apiPermission user
     */
    @DeleteMapping
    fun deletePurchase(@RequestParam id: Int): ResponseInfo<Int> {
        return ResponseInfo.ok(purchaseService.deletePurchase(id))
    }
}