package com.gyh.keepaccounts.service

import com.aliyun.oss.OSSClientBuilder
import java.io.InputStream

/**
 * Created by GYH on 2021/6/2
 */
object OSSClient {
    private const val endpoint = "https://oss-cn-shenzhen.aliyuncs.com"
    private const val bucket = "repair-h"
    private const val baseUrl = "https://repair-h.oss-cn-shenzhen.aliyuncs.com"
    private const val accessKeyId = "x"
    private const val accessKeySecret = "xx"
    const val path = "keepaccounts/"
    // 创建OSSClient实例。
    private val ossClient = OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret)

    fun updateFile(objectName : String, inputStream: InputStream): String {
        val putObject = ossClient.putObject(bucket, path + objectName.replace(" ", ""), inputStream)
        if (putObject.response.isSuccessful) {
            return path + objectName
        } else error("文件上传失败")
    }

    fun deleteFile(objectName : String): Boolean {
        return ossClient.deleteObject(bucket, objectName).response.isSuccessful
    }
}