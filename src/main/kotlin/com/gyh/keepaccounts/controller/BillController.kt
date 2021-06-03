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
}