package com.gyh.keepaccounts.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime

/**
 * ka_user
 * @author gyh
 * @apiDefine User
 * @apiParam {Integer} id 用户id
 * @apiParam {String} username 用户名
 * @apiParam {String} [password] 密码
 * @apiParam {String} phone 地址
 * @apiParam {String} location 电话
 * @apiParam {String} name 姓名
 * @apiParam {String} logistics 物流
 * @apiParam {String} remark 备注
 * @apiParam {Date} [createTime] 注册日期
 */
open class User(
    var id: Int? = null,

    /**
     * 名称
     */
    private var username: String? = null,

    /**
     * 密码
     */
    private var password: String? = null,

    /**
     * 地址
     */
    var location: String? = null,

    /**
     * 电话
     */
    var phone: String? = null,

    /**
     * 姓名
     */
    var name: String? = null,

    /**
     * 物流
     */
    var logistics: String? = null,

    /**
     * 图片地址，用空格分割
     */
    @JsonIgnore
    var imgs: String? = null,

    /**
     * 备注
     */
    var remark: String? = null,

    var createTime: LocalDateTime? = null
) : UserDetails {

    @JsonIgnore
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(role)
    }

    @JsonIgnore
    override fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String?) {
        this.password = password
    }

    override fun getUsername(): String? {
        return username
    }

    fun setUsername(username: String?) {
        this.username = username
    }

    @JsonIgnore
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isAccountNonLocked(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isEnabled(): Boolean {
        return true
    }
}

val role = GrantedAuthority { "ROLE_USER" }