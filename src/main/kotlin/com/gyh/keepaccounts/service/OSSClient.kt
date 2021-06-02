package com.gyh.keepaccounts.service

import com.aliyun.oss.OSSClientBuilder

/**
 * Created by GYH on 2021/6/2
 */
class OSSClient {
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    val endpoint = "https://oss-cn-hangzhou.aliyuncs.com"

    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
    val accessKeyId = "<yourAccessKeyId>"
    val accessKeySecret = "<yourAccessKeySecret>"

    // 创建OSSClient实例。
    val ossClient = OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret)

}