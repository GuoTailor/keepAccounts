package com.gyh.keepaccounts.service

import com.gyh.keepaccounts.model.User
import com.gyh.keepaccounts.model.view.UserResponseInfo
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

/**
 * Created by GYH on 2021/6/2
 */
@Service
class FileService {
    private val logger = LoggerFactory.getLogger(this.javaClass.simpleName)
    @Value("\${fileUploadPath}")
    lateinit var rootPath: String

    fun updateFiles(userId: Int, files: Array<MultipartFile>): Boolean {
        files.forEach { file ->
            val suffix = file.originalFilename?.split(".")?.let {
                if (it.lastIndex > 0) "." + it[it.lastIndex] else null
            }
            val fileName = UUID.randomUUID().toString() + (suffix ?: "")
            val dest = File("$rootPath${File.separator}$userId${File.separator}$fileName")
            if (!dest.parentFile.exists()) {
                val result = dest.parentFile.mkdirs()  //新建文件夹
                if (!result) return false
            }
            file.transferTo(dest.toPath())
        }
        return true
    }

    fun loadFile(user: User?): UserResponseInfo {
        return if (user?.id != null) {
            val root = File("$rootPath${File.separator}${user.id}")
            UserResponseInfo(user, root.list()?.asList() ?: listOf())
        } else UserResponseInfo(listOf())
    }

    fun deleteFile(path: String): Boolean {
        logger.info(path)
        return File(path).delete()
    }
}