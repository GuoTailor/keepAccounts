package com.gyh.keepaccounts.model.view

import com.gyh.keepaccounts.model.User
import org.springframework.beans.BeanUtils

/**
 * Created by GYH on 2021/6/1
 * @apiDefine UserInfo
 * @apiSuccess (返回) {Integer} id 用户id
 * @apiSuccess (返回) {Array} files 图片列表
 * @apiSuccess (返回) {String} username 用户名
 * @apiSuccess (返回) {String} password 密码
 * @apiSuccess (返回) {String} phone 地址
 * @apiSuccess (返回) {String} location 电话
 * @apiSuccess (返回) {String} name 姓名
 * @apiSuccess (返回) {String} logistics 物流
 * @apiSuccess (返回) {String} remark 备注
 * @apiSuccess (返回) {Date} createTime 注册日期
 */
data class UserResponseInfo(val files: List<String>): User() {
    constructor(user: User, files: List<String>) : this(files) {
        BeanUtils.copyProperties(user, this)
    }
}