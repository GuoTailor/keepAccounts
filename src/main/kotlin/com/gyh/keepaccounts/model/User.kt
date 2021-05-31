package com.gyh.keepaccounts.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime


/**
 * ka_user
 * @author
 */
data class User(
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
     * 备注
     */
    var remark: String? = null,

    var createTime: LocalDateTime? = null
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(role)
    }

    override fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }

    override fun getUsername(): String? {
        return username
    }

    fun setUsername(username: String) {
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