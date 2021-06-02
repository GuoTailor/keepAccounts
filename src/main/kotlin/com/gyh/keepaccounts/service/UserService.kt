package com.gyh.keepaccounts.service

import com.github.pagehelper.PageHelper
import com.gyh.keepaccounts.common.Util.getCurrentUser
import com.gyh.keepaccounts.mapper.UserMapper
import com.gyh.keepaccounts.model.PageView
import com.gyh.keepaccounts.model.ResponseInfo
import com.gyh.keepaccounts.model.User
import com.gyh.keepaccounts.model.view.UserResponseInfo
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*
import javax.annotation.Resource

/**
 * Created by gyh on 2021/2/3
 */
@Service
class UserService(val passwordEncoder: PasswordEncoder) : UserDetailsService {
    private val logger = LoggerFactory.getLogger(this.javaClass.simpleName)

    @Value("\${fileUploadPath}")
    lateinit var rootPath: String

    @Autowired
    lateinit var fileService: FileService

    @Resource
    lateinit var userMapper: UserMapper

    override fun loadUserByUsername(s: String): UserResponseInfo {
        val user = userMapper.loadUserByUsername(s)
            ?: throw UsernameNotFoundException("用户：" + s + "不存在")
        return fileService.loadFile(user)
    }

    fun getAllUser(page: Int, size: Int): PageView<UserResponseInfo> {
        PageHelper.startPage<Any>(page, size)
        return PageView.build(userMapper.findAll().map { fileService.loadFile(it) })
    }

    fun getById(id: Int) = fileService.loadFile(userMapper.selectByPrimaryKey(id) ?: error("用户：" + id + "不存在"))

    /**
     * 添加用户
     * @param user user
     * @return user
     */
    fun register(user: UserResponseInfo): ResponseInfo<*> {
        user.password?.let { user.password = passwordEncoder.encode(it) }
        user.imgs = user.files?.joinToString(separator = " ") { it }
        userMapper.insertSelective(user)
        return ResponseInfo.ok(user)
    }

    /**
     * 更新用户
     */
    fun update(user: UserResponseInfo): Int {
        user.imgs = user.files?.joinToString(separator = " ") { it }
        user.password?.let { user.password = passwordEncoder.encode(it) }
        return userMapper.updateByPrimaryKeySelective(user)
    }

    /**
     * 删除用户
     */
    fun deleteUser(id: Int): Int {
        val user = userMapper.selectByPrimaryKey(id)
        fileService.deleteAll(user?.imgs ?: "")
        return userMapper.deleteByPrimaryKey(id)
    }

    fun uploadFile(files: Array<MultipartFile>): List<String> {
        return fileService.updateFiles(files)
    }

    fun uploadFile(file: MultipartFile): String {
        return fileService.updateFile(file)
    }

    fun deleteFile(path: String) {
        fileService.deleteFile(path)
    }
}