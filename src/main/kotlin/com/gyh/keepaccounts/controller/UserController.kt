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
     * {"code": 1,"msg": "成功","data": [{"consume": null,"id": 2,"username": "cheshi","location": "重庆","phone":
     * "1234566","name": "张三","logistics": "物流","remark": "备注","createTime": null,"files": []},{"consume": 208.00,
     * "id": 1,"username": "admin","location": null,"phone": null,"name": null,"logistics": null,"remark": null,
     * "createTime": null,"files": []},{"consume": 208.00,"id": 6,"username": "李李","location": "123","phone": "",
     * "name": "123","logistics": "","remark": "","createTime": null,"files": ["","keepaccounts/1755a64d-2d5a-4854-9c61-771446a28bc1.jpg"]}
     * ,{"consume": 208.00,"id": 11,"username": "添加客户","location": "1","phone": "1","name": "客户名称","logistics": ""
     * ,"remark": "","createTime": null,"files": [""]},{"consume": 220.00,"id": 12,"username": "李三","location": "",
     * "phone": "","name": "李三","logistics": "","remark": "","createTime": null,"files": [""]},{"consume": 316.00,"id"
     * : 13,"username": "三麻子","location": "","phone": "","name": "三麻子","logistics": "","remark": "","createTime":
     * null,"files": [""]},{"consume": 431.50,"id": 4,"username": "小三","location": "北京","phone": "1999405","name":
     * "小三","logistics": "8我屋","remark": "备注","createTime": null,"files": [""]},{"consume": 812.00,"id": 10,"username"
     * : "重庆机电科技有限公司","location": "重庆市沙坪坝区大学城中路","phone": "1582343433","name": "张三6","logistics": "安顺",
     * "remark": "备注内容","createTime": null,"files": [""]},{"consume": 1262.50,"id": 9,"username": "河北科技2","location":
     * "河北保定市","phone": "134632","name": "张三3","logistics": "安顺","remark": "备注撒","createTime": null,"files": [""]}]}
     * @apiGroup User
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
     * @api {get} /user/id 根据id查询用户
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