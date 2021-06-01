package com.gyh.keepaccounts.controller

import com.gyh.keepaccounts.model.PageView
import com.gyh.keepaccounts.model.ResponseInfo
import com.gyh.keepaccounts.model.User
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
     * {"code": 0,"msg": "成功","data": []}
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
    @GetMapping("/all")
    fun getAllUser(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?
    ): ResponseInfo<PageView<User>> {
        return ResponseInfo.ok(userService.getAllUser(page ?: 1, size ?: 30))
    }

    /**
     * @api {get} /user/username 根据客户名称查询用户
     * @apiDescription 根据客户名称查询用户
     * @apiName getByUsername
     * @apiVersion 0.0.1
     * @apiParam {String} username 客户名称
     * @apiSuccessExample {json} 成功返回:
     * {"code": 0,"msg": "成功","data": {}}
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
    @GetMapping("/username")
    fun getByUsername(@RequestParam username: String): ResponseInfo<UserDetails> {
        return ResponseInfo.ok(userService.loadUserByUsername(username))
    }

    /**
     * @api {get} /user/username 根据id查询用户
     * @apiDescription 根据id查询用户
     * @apiName getById
     * @apiVersion 0.0.1
     * @apiParam {Integer} id 客户id
     * @apiSuccessExample {json} 成功返回:
     * {"code": 0,"msg": "成功","data": {}}
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
    fun getById(@RequestParam id: Int): ResponseInfo<User?> {
        return ResponseInfo.ok(userService.getById(id))
    }

    /**
     * @api {post} /user 添加用户
     * @apiDescription 添加用户
     * @apiName addUser
     * @apiVersion 0.0.1
     * @apiUse User
     * @apiParam {File} files 图片列表
     * @apiSuccessExample {json} 成功返回:
     * {"code": 0,"msg": "成功","data": {}}
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
    @PostMapping
    fun addUser(
        @RequestParam("username") username: String,
        @RequestParam("password", required = false) password: String?,
        @RequestParam("location", required = false) location: String?,
        @RequestParam("phone", required = false) phone: String?,
        @RequestParam("name", required = false) name: String?,
        @RequestParam("logistics", required = false) logistics: String?,
        @RequestParam("createTime", required = false) createTime: LocalDateTime?,
        @RequestParam("files") files: Array<MultipartFile>
    ): ResponseInfo<*> {
        val user = User(
            username = username,
            password = password,
            location = location,
            phone = phone,
            name = name,
            logistics = logistics,
            createTime = createTime
        )
        return userService.register(user, files)
    }

    /**
     * @api {put} /user 更新用户
     * @apiDescription 更新用户
     * @apiName updateUser
     * @apiVersion 0.0.1
     * @apiUse User
     * @apiParam {File} files 图片列表
     * @apiSuccessExample {json} 成功返回:
     * {"code": 0,"msg": "成功","data": {}}
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
    @PutMapping
    fun updateUser(@RequestParam("username") username: String,
                   @RequestParam("password", required = false) password: String?,
                   @RequestParam("location", required = false) location: String?,
                   @RequestParam("phone", required = false) phone: String?,
                   @RequestParam("name", required = false) name: String?,
                   @RequestParam("logistics", required = false) logistics: String?,
                   @RequestParam("createTime", required = false) createTime: LocalDateTime?,
                   @RequestParam("files") files: Array<MultipartFile>): ResponseInfo<Int> {
        val user = User(
            username = username,
            password = password,
            location = location,
            phone = phone,
            name = name,
            logistics = logistics,
            createTime = createTime
        )
        return ResponseInfo.ok(userService.update(user, files))
    }

    /**
     * @api {delete} /user/file 删除用户图片
     * @apiDescription 删除用户图片
     * @apiName deleteFile
     * @apiVersion 0.0.1
     * @apiParam {String} path 图片路径
     * @apiSuccessExample {json} 成功返回:
     * {"code": 0,"msg": "成功","data": {}}
     * @apiGroup User
     * @apiPermission user
     */
    @DeleteMapping("/file")
    fun deleteFile(path: String): ResponseInfo<Any> {
        return ResponseInfo.ok(userService.deleteFile(path))
    }

    /**
     * @api {delete} /user 删除用户
     * @apiDescription 删除用户
     * @apiName deleteUser
     * @apiVersion 0.0.1
     * @apiParam {Integer} id id
     * @apiSuccessExample {json} 成功返回:
     * {"code": 0,"msg": "成功","data": {}}
     * @apiGroup User
     * @apiPermission user
     */
    @DeleteMapping
    fun deleteUser(@RequestParam id: Int): ResponseInfo<Int> {
        return ResponseInfo.ok(userService.deleteUser(id))
    }
}