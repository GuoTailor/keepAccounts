package com.gyh.keepaccounts.service

import com.qcloud.cos.COSClient
import com.qcloud.cos.ClientConfig
import com.qcloud.cos.auth.BasicCOSCredentials
import com.qcloud.cos.http.HttpProtocol
import com.qcloud.cos.model.DeleteObjectsRequest
import com.qcloud.cos.model.ObjectMetadata
import com.qcloud.cos.model.PutObjectRequest
import com.qcloud.cos.region.Region
import java.io.InputStream


/**
 * Created by GYH on 2021/6/8
 */
object COSClient {
    // 1 初始化用户身份信息（secretId, secretKey）。
    private const val secretId = "**"
    private const val secretKey = "***"
    private const val bucket = "repair-h-1306167226" //存储桶名称，格式：BucketName-APPID
    const val path = "keepaccounts/"

    // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
    // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
    private val region = Region("ap-nanjing")
    private val cred = BasicCOSCredentials(secretId, secretKey)
    private val clientConfig = ClientConfig(region)
    private val cosClient = COSClient(cred, clientConfig)

    init {
        // 这里建议设置使用 https 协议
        clientConfig.httpProtocol = HttpProtocol.https
    }

    fun updateFile(objectName: String, inputStream: InputStream, size: Long): String {
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentLength = size
        val putObjectRequest = PutObjectRequest(bucket, path + objectName, inputStream, objectMetadata)
        cosClient.putObject(putObjectRequest)
        return path + objectName
    }

    fun deleteFile(objectName: String) {
        cosClient.deleteObject(bucket, objectName)
    }

    fun deleteFile(objectNames: List<String>) {
        val deleteObjectsRequest = DeleteObjectsRequest(bucket)
        // 设置要删除的key列表, 最多一次删除1000个
        deleteObjectsRequest.keys = objectNames.map { DeleteObjectsRequest.KeyVersion(it) }
        // 批量删除文件
        cosClient.deleteObjects(deleteObjectsRequest)
    }


}