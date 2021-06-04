package com.gyh.keepaccounts.controller

import com.gyh.keepaccounts.model.PageView
import com.gyh.keepaccounts.model.ResponseInfo
import com.gyh.keepaccounts.model.User
import com.gyh.keepaccounts.model.view.UserConsume
import com.gyh.keepaccounts.model.view.UserResponseInfo
import com.gyh.keepaccounts.service.UserService
import org.apache.coyote.RequestInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

/**
 * Created by gyh on 2021/5/31
 */
@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    lateinit var userService: UserService

    /**
     * @api {get} /user/all 分页查询用户
     * @apiDescription 分页查询所有用户
     * @apiName getAllUser
     * @apiVersion 0.0.1
     * @apiParam {Integer} [page] 第几页(从一开始)
     * @apiParam {Integer} [size] 每页大小
     * @apiSuccessExample {json} 成功返回:
     * {"code": 1,"msg": "成功","data": {"pageNum": 1,"pageSize": 2,"total": 2,"list": [{"files": [],"id": 1,"username":
     * "admin","location": null,"phone": null,"name": null,"logistics": null,"remark": null,"createTime": 1622444824000}
     * ,{"files": ["keepaccounts/bd5de385-3696-457b-89f3-aaf420255a6c.jpg"],"id": 3,"username": "测试","location": "重庆"
     * ,"phone": "1234566","name": "张三","logistics": "物流","remark": "备注","createTime": 1622685018000}],"pages": 1}}
     * @apiSuccess (返回) {Integer} id 用户id
     * @apiSuccess (返回) {String} username 用户名
     * @apiSuccess (返回) {String} password 密码
     * @apiSuccess (返回) {String} phone 地址
     * @apiSuccess (返回) {String} location 电话
     * @apiSuccess (返回) {String} name 姓名
     * @apiSuccess (返回) {String} logistics 物流
     * @apiSuccess (返回) {String} remark 备注
     * @apiSuccess (返回) {Date} createTime 注册日期
     * @apiUse PageView
     * @apiGroup User
     * @apiPermission user
     */
    @GetMapping("/all")
    fun getAllUser(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?
    ): ResponseInfo<PageView<UserResponseInfo>> {
        return ResponseInfo.ok(userService.getAllUser(page ?: 1, size ?: 30))
    }

    /**
     * @api {get} /user/consume 统计消费记录
     * @apiDescription 统计消费记录
     * @apiName findConsume
     * @apiVersion 0.0.1
     * @apiParam {Integer} [page] 第几页(从一开始)
     * @apiParam {Integer} [size] 每页大小
     * @apiParam {String} field 排序字段支持 consume，create_time，
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
    @GetMapping("/consume")
    fun findConsume(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?,
        @RequestParam field: String,
        @RequestParam order: String
    ): ResponseInfo<List<UserConsume>> {
        return ResponseInfo.ok(userService.findConsume(page ?: 1, size ?: 30, field, order))
    }

    /**
     * @api {get} /user/username 根据客户名称查询用户
     * @apiDescription 根据客户名称查询用户
     * @apiName getByUsername
     * @apiVersion 0.0.1
     * @apiParam {String} username 客户名称
     * @apiSuccessExample {json} 成功返回:
     * {"code": 1,"msg": "成功","data": [{"files": ["keepaccounts/bd5de385-3696-457b-89f3-aaf420255a6c.jpg"],"id": 3,"username":
     * "测试","location": "重庆","phone": "1234566","name": "张三","logistics": "物流","remark": "备注","createTime": 1622685018000}]}
     * @apiSuccess (返回) {Integer} id 用户id
     * @apiSuccess (返回) {String} username 用户名
     * @apiSuccess (返回) {String} password 密码
     * @apiSuccess (返回) {List} files 图片地址，需要拼接https://repair-h.oss-cn-shenzhen.aliyuncs.com/
     * @apiSuccess (返回) {String} phone 地址
     * @apiSuccess (返回) {String} location 电话
     * @apiSuccess (返回) {String} name 姓名
     * @apiSuccess (返回) {String} logistics 物流
     * @apiSuccess (返回) {String} remark 备注
     * @apiSuccess (返回) {Date} createTime 注册日期
     * @apiGroup User
     * @apiPermission user
     */
    @GetMapping("/username")
    fun getByUsername(@RequestParam username: String): ResponseInfo<List<UserResponseInfo>> {
        return ResponseInfo.ok(userService.findUserByUsername(username))
    }

    /**
     * @api {get} /user/username 根据id查询用户
     * @apiDescription 根据id查询用户
     * @apiName getById
     * @apiVersion 0.0.1
     * @apiParam {Integer} id 客户id
     * @apiSuccessExample {json} 成功返回:
     * {"code": 1,"msg": "成功","data": {}}
     * @apiSuccess (返回) {Integer} id 用户id
     * @apiSuccess (返回) {String} username 用户名
     * @apiSuccess (返回) {String} password 密码
     * @apiSuccess (返回) {String} phone 地址
     * @apiSuccess (返回) {String} location 电话
     * @apiSuccess (返回) {String} name 姓名
     * @apiSuccess (返回) {String} logistics 物流
     * @apiSuccess (返回) {String} remark 备注
     * @apiSuccess (返回) {Date} createTime 注册日期
     * @apiGroup User
     * @apiPermission user
     */
    @GetMapping("/id")
    fun getById(@RequestParam id: Int): ResponseInfo<UserResponseInfo?> {
        return ResponseInfo.ok(userService.getById(id))
    }

    /**
     * @api {post} /user 添加用户
     * @apiDescription 添加用户
     * @apiName addUser
     * @apiVersion 0.0.1
     * @apiUse User
     * @apiParam {String} files 图片列表
     * @apiParamExample {json} 请求示例:
     * {"username": "cheshi","phone": "1234566","location": "重庆","name": "张三","logistics": "物流","files":
     * ["keepaccounts/bd5de385-3696-457b-89f3-aaf420255a6c.jpg"],"remark": "备注"}
     * @apiSuccessExample {json} 成功返回:
     * {"code": 1,"msg": "成功","data": {}}
     * @apiSuccess (返回) {Integer} id 用户id
     * @apiSuccess (返回) {String} username 用户名
     * @apiSuccess (返回) {String} password 密码
     * @apiSuccess (返回) {List} files 图片地址，需要拼接https://repair-h.oss-cn-shenzhen.aliyuncs.com/
     * @apiSuccess (返回) {String} phone 地址
     * @apiSuccess (返回) {String} location 电话
     * @apiSuccess (返回) {String} name 姓名
     * @apiSuccess (返回) {String} logistics 物流
     * @apiSuccess (返回) {String} remark 备注
     * @apiSuccess (返回) {Date} createTime 注册日期
     * @apiGroup User
     * @apiPermission user
     */
    @PostMapping
    fun addUser(@RequestBody user: UserResponseInfo): ResponseInfo<UserResponseInfo> {
        return userService.register(user)
    }

    /**
     * @api {put} /user 更新用户
     * @apiDescription 更新用户
     * @apiName updateUser
     * @apiVersion 0.0.1
     * @apiUse User
     * @apiParam {String} files 图片列表
     * @apiParamExample {json} 请求示例:
     * {"id": 3,"username": "测试","phone": "1234566","location": "重庆","name": "张三","logistics": "物流","files":
     * ["keepaccounts/bd5de385-3696-457b-89f3-aaf420255a6c.jpg"],"remark": "备注"}
     * @apiSuccessExample {json} 成功返回:
     * {"code": 1,"msg": "成功","data": 1}
     * @apiGroup User
     * @apiPermission user
     */
    @PutMapping
    fun updateUser(@RequestBody user: UserResponseInfo): ResponseInfo<Int> {
        return ResponseInfo.ok(userService.update(user))
    }

    /**
     * @api {delete} /user/file 删除用户图片
     * @apiDescription 删除用户图片
     * @apiName deleteFile
     * @apiVersion 0.0.1
     * @apiParam {String} path 图片路径
     * @apiSuccessExample {json} 成功返回:
     * {"code": 1,"msg": "成功","data": {}}
     * @apiGroup User
     * @apiPermission user
     */
    @DeleteMapping("/file")
    fun deleteFile(path: String): ResponseInfo<Any> {
        return ResponseInfo.ok(userService.deleteFile(path))
    }

    /**
     * @api {post} /user/files 上传多个用户图片
     * @apiDescription 上传多个用户图片
     * @apiName uploadFiles
     * @apiVersion 0.0.1
     * @apiParam {File} files 图片
     * @apiSuccessExample {json} 成功返回:
     * {"code": 1,"msg": "成功","data": ["1.png", "2.png"]}
     * @apiGroup User
     * @apiPermission user
     */
    @PostMapping("/files")
    fun uploadFiles(@RequestParam files: Array<MultipartFile>): ResponseInfo<List<String>> {
        return ResponseInfo.ok(userService.uploadFile(files))
    }

    /**
     * @api {post} /user/file 上传单个用户图片
     * @apiDescription 上传单个用户图片
     * @apiName uploadFile
     * @apiVersion 0.0.1
     * @apiParam {File} file 图片
     * @apiSuccessExample {json} 成功返回:
     * {"code": 1,"msg": "成功","data": "keepaccounts/8ae87fdc-1c6e-44b7-9e68-bedab2c92e9e.jpeg"}
     * @apiGroup User
     * @apiPermission user
     */
    @PostMapping("/file")
    fun uploadFile(@RequestParam file: MultipartFile): ResponseInfo<String> {
        return ResponseInfo.ok(userService.uploadFile(file))
    }

    /**
     * @api {delete} /user 删除用户
     * @apiDescription 删除用户
     * @apiName deleteUser
     * @apiVersion 0.0.1
     * @apiParam {Integer} id id
     * @apiSuccessExample {json} 成功返回:
     * {"code": 1,"msg": "成功","data": {}}
     * @apiGroup User
     * @apiPermission user
     */
    @DeleteMapping
    fun deleteUser(@RequestParam id: Int): ResponseInfo<Int> {
        return ResponseInfo.ok(userService.deleteUser(id))
    }
}