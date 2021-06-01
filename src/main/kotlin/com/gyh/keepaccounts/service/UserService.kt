package com.gyh.keepaccounts.service

import com.github.pagehelper.PageHelper
import com.gyh.keepaccounts.common.Util.getCurrentUser
import com.gyh.keepaccounts.mapper.UserMapper
import com.gyh.keepaccounts.model.PageView
import com.gyh.keepaccounts.model.ResponseInfo
import com.gyh.keepaccounts.model.User
import org.slf4j.LoggerFactory
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

    @Resource
    lateinit var userMapper: UserMapper

    override fun loadUserByUsername(s: String): UserDetails {
        val user = userMapper.loadUserByUsername(s)
            ?: throw UsernameNotFoundException("用户：" + s + "不存在")

        return user
    }

    fun getAllUser(page: Int, size: Int): PageView<User> {
        PageHelper.startPage<Any>(page, size)
        return PageView.build(userMapper.findAll())
    }

    fun getById(id: Int) = userMapper.selectByPrimaryKey(id)

    /**
     * 添加用户
     * @param user user
     * @return user
     */
    fun register(user: User, files: Array<MultipartFile>): ResponseInfo<*> {
        user.password?.let { user.setPassword(passwordEncoder.encode(it)) }
        userMapper.insertSelective(user)
        updateFiles(user.id!!, files)
        return ResponseInfo.ok(user)
    }

    /**
     * 更新用户
     */
    fun update(user: User, files: Array<MultipartFile>?): Int {
        val userId = user.id!!
        user.password?.let { user.setPassword(passwordEncoder.encode(it)) }
        if (files?.isNotEmpty() == true) {
            updateFiles(userId, files)
        }
        return userMapper.updateByPrimaryKeySelective(user)
    }

    fun deleteFile(path: String) {
        logger.info(path)
    }

    fun deleteUser(id: Int): Int {
        val root = File("$rootPath${File.separator}$id")
        root.listFiles()?.map { it.delete() }
        root.delete()
        return userMapper.deleteByPrimaryKey(id)
    }

    fun updateFiles(userId: Int, files: Array<MultipartFile>): Boolean {
        files.forEach { file ->
            val root = File("$rootPath${File.separator}$userId")
            val suffix = file.originalFilename?.split(".")?.let {
                if (it.lastIndex > 0) "." + it[it.lastIndex] else null
            }
            val list = root.list()
            var index = 1
            if (list == null) {
                root.mkdirs()
            } else {
                val suffixs =
                    list.map { f -> f.split(".").let { if (it.lastIndex > 0) "." + it[it.lastIndex] else f } }
                while (!suffixs.contains(index.toString())) {
                    index++
                }
            }
            val dest = File("$rootPath${File.separator}$userId${File.separator}$index$suffix")
            if (!dest.parentFile.exists()) {
                val result = dest.parentFile.mkdirs()  //新建文件夹
                if (!result) return false
            }
            file.transferTo(dest.toPath())
        }
        return true
    }

    fun loadFile(user: User) {
        if (user.id != null) {
            val root = File("$rootPath${File.separator}${user.id}")
        }
    }
}