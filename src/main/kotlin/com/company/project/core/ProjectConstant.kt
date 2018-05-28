package com.company.project.core

/**
 * 项目常量
 */
object ProjectConstant {
    val BASE_PACKAGE = "com.company.project"//生成代码所在的基础包名称，可根据自己公司的项目修改（注意：这个配置修改之后需要手工修改src目录项目默认的包路径，使其保持一致，不然会找不到类）

    val MODEL_PACKAGE = "$BASE_PACKAGE.model"//生成的Model所在包
    val MAPPER_PACKAGE = "$BASE_PACKAGE.dao"//生成的Mapper所在包
    val SERVICE_PACKAGE = "$BASE_PACKAGE.service"//生成的Service所在包
    val SERVICE_IMPL_PACKAGE = "$SERVICE_PACKAGE.impl"//生成的ServiceImpl所在包
    val CONTROLLER_PACKAGE = "$BASE_PACKAGE.web"//生成的Controller所在包

    val MAPPER_INTERFACE_REFERENCE = "$BASE_PACKAGE.core.Mapper"//Mapper插件基础接口的完全限定名
}
