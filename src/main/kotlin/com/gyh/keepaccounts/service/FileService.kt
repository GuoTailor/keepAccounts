package com.gyh.keepaccounts.service

import com.gyh.keepaccounts.model.User
import com.gyh.keepaccounts.model.view.UserResponseInfo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

/**
 * Created by GYH on 2021/6/2
 */
@Service
class FileService {
    private val logger = LoggerFactory.getLogger(this.javaClass.simpleName)

    fun updateFiles(files: Array<MultipartFile>): List<String> {
        return files.map { file -> updateFile(file) }
    }

    fun updateFile(file: MultipartFile): String {
        val suffix = file.originalFilename?.split(".")?.let {
            if (it.lastIndex > 0) "." + it[it.lastIndex] else null
        }
        val fileName = UUID.randomUUID().toString() + (suffix ?: "")
        return COSClient.updateFile(fileName, file.inputStream, file.size)
    }

    fun loadFile(user: User): UserResponseInfo {
        val root = user.imgs?.split(" ")
        return UserResponseInfo(user, root ?: listOf())

    }

    fun deleteFile(path: String) = COSClient.deleteFile(path)

    fun deleteAll(files: String) {
        if (files.isNotBlank()) {
            val objectNames = files.split(" ")
            return COSClient.deleteFile(objectNames)
        }
    }
}