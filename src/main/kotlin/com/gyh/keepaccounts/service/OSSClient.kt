package com.gyh.keepaccounts.service

import com.aliyun.oss.OSSClientBuilder
import com.aliyun.oss.model.DeleteObjectsRequest
import java.io.InputStream


/**
 * Created by GYH on 2021/6/2
 */
object OSSClient {
    private const val endpoint = "https://oss-cn-shenzhen.aliyuncs.com"
    private const val bucket = "repair-h"
    private const val baseUrl = "https://repair-h.oss-cn-shenzhen.aliyuncs.com"
    private const val accessKeyId = "*"
    private const val accessKeySecret = "*"
    const val path = "keepaccounts/"

    // 创建OSSClient实例。
    private val ossClient = OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret)

    fun updateFile(objectName: String, inputStream: InputStream): String {
        ossClient.putObject(bucket, path + objectName.replace(" ", ""), inputStream)
        return path + objectName
    }

    fun deleteFile(objectName: String): Boolean {
        return ossClient.deleteObject(bucket, objectName).requestId != null
    }

    fun deleteFile(objectNames: List<String>) {
        val deleteObjectsRequest = DeleteObjectsRequest(bucket)
        deleteObjectsRequest.keys = objectNames
        deleteObjectsRequest.isQuiet = true
        ossClient.deleteObjects(deleteObjectsRequest)
        ossClient.deleteObject(bucket, path)
    }
}