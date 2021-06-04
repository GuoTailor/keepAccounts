package com.gyh.keepaccounts.controller

import com.gyh.keepaccounts.model.Bill
import com.gyh.keepaccounts.model.PageView
import com.gyh.keepaccounts.model.ResponseInfo
import com.gyh.keepaccounts.model.view.BillRequestInfo
import com.gyh.keepaccounts.model.view.BillResponseInfo
import com.gyh.keepaccounts.service.BillService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * Created by gyh on 2021/6/1
 */
@RestController
@RequestMapping("/bill")
class BillController {
    @Autowired
    lateinit var billService: BillService

    /**
     * @api {post} /bill 添加账单
     * @apiDescription 添加账单
     * @apiName createBill
     * @apiVersion 0.0.1
     * @apiUse BillRequestInfo
     * @apiParamExample {json} 请求示例:
     * {"userId": 3,"billList": [{"type": "宝马","specification": "大","amount": 2,"price": 800000.01},{"type": "宝马",
     * "specification": "大","amount": 3,"price": 800000.01}],"remark": "备注","paymentType": "wzf"}
     * @apiSuccessExample {json} 成功返回:
     * {"code": 1,"msg": "成功","data": {}}
     * @apiGroup Bill
     * @apiPermission user
     */
    @PostMapping
    fun createBill(@RequestBody bill: BillRequestInfo): ResponseInfo<Int> {
        return ResponseInfo.ok(billService.createBill(bill))
    }

    /**
     * @api {put} /bill 更新账单
     * @apiDescription 更新账单
     * @apiName updateBill
     * @apiVersion 0.0.1
     * @apiUse Bill
     * @apiParamExample {json} 请求示例:
     * {"id": 3,"type": "宝马","specification": "小","paymentType": "wx","createTime": 1622691989000}
     * @apiSuccessExample {json} 成功返回:
     * {"code": 1,"msg": "成功","data": {}}
     * @apiGroup Bill
     * @apiPermission user
     */
    @PutMapping
    fun updateBill(@RequestBody bill: Bill): ResponseInfo<Int> {
        return ResponseInfo.ok(billService.updateBill(bill))
    }

    /**
     * @api {get} /bill/all 分页查询账单
     * @apiDescription 分页查询账单
     * @apiName getAllBill
     * @apiVersion 0.0.1
     * @apiParam {Integer} [page] 第几页(从一开始)
     * @apiParam {Integer} [size] 每页大小
     * @apiParam {Integer} [userId] 用户id，不传查询所有
     * @apiSuccessExample {json} 成功返回:
     * {"code": 1,"msg": "成功","data": {"pageNum": 1,"pageSize": 30,"total": 2,"list": [{"username": "测试","debt": 3,
     * "id": 3,"userId": 3,"type": "宝马","specification": "大","amount": 2,"price": 800000.01,"remark": "备注","payment"
     * : 0.00,"paymentType": "wzf","createTime": 1622691796000},{"username": "测试","debt": 3,"id": 4,"userId": 3,"type"
     * : "宝马","specification": "大","amount": 3,"price": 800000.01,"remark": "备注","payment": 0.00,"paymentType": "wzf"
     * ,"createTime": 1622691796000}],"pages": 1}}
     * @apiUse BillResponseInfo
     * @apiUse PageView
     * @apiGroup Bill
     * @apiPermission user
     */
    @GetMapping("/all")
    fun getAllBill(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?,
        @RequestParam(required = false) userId: Int?
    ): ResponseInfo<PageView<BillResponseInfo>> {
        return ResponseInfo.ok(billService.getAllBill(page ?: 1, size ?: 30, userId))
    }

    /**
     * @api {get} /bill 根据id查询账单
     * @apiDescription 根据id查询账单
     * @apiName getBill
     * @apiVersion 0.0.1
     * @apiParam {Integer} id 账单id
     * @apiSuccessExample {json} 成功返回:
     * {"code": 1,"msg": "成功","data": {}}
     * @apiUse BillResponseInfo
     * @apiGroup Bill
     * @apiPermission user
     */
    @GetMapping
    fun getBill(@RequestParam id: Int): ResponseInfo<BillResponseInfo> {
        return ResponseInfo.ok(billService.getBill(id))
    }

    /**
     * @api {delete} /bill 根据id删除账单
     * @apiDescription 根据id删除账单
     * @apiName deleteBill
     * @apiVersion 0.0.1
     * @apiParam {Integer} id 账单id
     * @apiSuccessExample {json} 成功返回:
     * {"code": 1,"msg": "成功","data": {}}
     * @apiGroup Bill
     * @apiPermission user
     */
    @DeleteMapping
    fun deleteBill(id: Int): ResponseInfo<Int> {
        return ResponseInfo.ok(billService.deleteBill(id))
    }

    /**
     * @api {get} /bill/statistics 统计
     * @apiDescription 统计
     * @apiName countSalesVolume
     * @apiVersion 0.0.1
     * @apiSuccessExample {json} 成功返回:
     * {"code": 1,"msg": "成功","data": {"accountPaid":1200.89, "monthSell":10400000.13, "nonPayment":8799990.11, "arrearage":120.89, "daySell":0}}
     * @apiGroup Bill
     * @apiSuccess (返回) {Decimal} accountPaid 未付款⾦额
     * @apiSuccess (返回) {Decimal} arrearage 已付款⾦额
     * @apiSuccess (返回) {Decimal} monthSell 月销售额
     * @apiSuccess (返回) {Decimal} daySell 日销售额
     * @apiSuccess (返回) {Decimal} nonPayment 未收款⾦额
     * @apiPermission user
     */
    @GetMapping("/statistics")
    fun countSalesVolume(): ResponseInfo<MutableMap<String, Any>> {
        return ResponseInfo.ok(billService.countSalesVolume())
    }

    /**
     * @api {get} /bill/debt 统计未收款记录
     * @apiDescription 统计未收款记录
     * @apiName findDebt
     * @apiVersion 0.0.1
     * @apiParam {Integer} [page] 第几页(从一开始)
     * @apiParam {Integer} [size] 每页大小
     * @apiParam {String} field 排序字段支持 price，create_time
     * @apiParam {String} order ASC:升序 DESC：降序
     * @apiSuccessExample {json} 成功返回:
     * {"code": 1,"msg": "成功","data": {"pageNum": 1,"pageSize": 30,"total": 4,"list": [{"username": "测试","debt": 3,
     * "id": 4,"userId": 3,"type": "宝马","specification": "大","amount": 3,"price": 800000.01,"remark": "备注","payment":
     * 0.00,"paymentType": "wzf","createTime": 1622691796000},{"username": "测试","debt": 3,"id": 6,"userId": 3,"type":
     * "宝马","specification": "小","amount": 3,"price": 800000.01,"remark": "备注","payment": 10.00,"paymentType": "wzf",
     * "createTime": 1622691989000},{"username": "测试","debt": 3,"id": 7,"userId": 3,"type": "宝马","specification": "小"
     * ,"amount": 2,"price": 800000.01,"remark": "备注","payment": 0.00,"paymentType": "wzf","createTime": 1622692050000}
     * ,{"username": "测试","debt": 3,"id": 8,"userId": 3,"type": "宝马","specification": "小","amount": 3,"price": 800000.01
     * ,"remark": "备注","payment": 0.00,"paymentType": "wzf","createTime": 1622692050000}],"pages": 1}}
     * @apiGroup Bill
     * @apiUse BillResponseInfo
     * @apiPermission user
     */
    @GetMapping("/debt")
    fun findDebt(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?,
        @RequestParam field: String,
        @RequestParam order: String
    ): ResponseInfo<PageView<BillResponseInfo>> {
        return ResponseInfo.ok(billService.findDebt(page ?: 1, size ?: 30, field, order))
    }
}