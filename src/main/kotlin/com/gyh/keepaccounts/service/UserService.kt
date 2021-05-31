package com.gyh.keepaccounts.service

import com.gyh.keepaccounts.common.Util.getCurrentUser
import com.gyh.keepaccounts.mapper.UserMapper
import com.gyh.keepaccounts.model.User
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.annotation.Resource

/**
 * Created by gyh on 2021/2/3
 */
@Service
class UserService(val passwordEncoder: PasswordEncoder) : UserDetailsService {
    private val logger = LoggerFactory.getLogger(this.javaClass.simpleName)

    @Resource
    lateinit var userMapper: UserMapper

    override fun loadUserByUsername(s: String): UserDetails {
        return userMapper.loadUserByUsername(s)
            ?: throw UsernameNotFoundException("用户：" + s + "不存在")
    }

    /**
     * 添加用户
     * @param user user
     * @return user
     */
    fun register(user: User): User {
        user.password?.let { user.setPassword(passwordEncoder.encode(it)) }
        userMapper.insertSelective(user)
        return user
    }

    fun update(user: User): Int {
        val id: Int = getCurrentUser().id!!
        user.password?.let { user.setPassword(passwordEncoder.encode(it)) }
        return userMapper.updateByPrimaryKeySelective(user)
    }

}