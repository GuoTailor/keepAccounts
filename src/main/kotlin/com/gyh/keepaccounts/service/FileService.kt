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

    fun updateFiles(userId: Int, files: Array<MultipartFile>): List<String> {
        return files.map { file ->
            val suffix = file.originalFilename?.split(".")?.let {
                if (it.lastIndex > 0) "." + it[it.lastIndex] else null
            }
            val fileName = userId.toString() + "/" + UUID.randomUUID().toString() + (suffix ?: "")
            OSSClient.updateFile(fileName, file.inputStream)
        }
    }

    fun loadFile(user: User?): UserResponseInfo {
        return if (user?.id != null) {
            val root = user.imgs?.split(" ")
            UserResponseInfo(user, root ?: listOf())
        } else UserResponseInfo(listOf())
    }

    fun deleteFile(path: String): Boolean {
        return OSSClient.deleteFile(path)
    }
}