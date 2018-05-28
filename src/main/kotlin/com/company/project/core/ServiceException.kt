package com.company.project.core

/**
 * 服务（业务）异常如“ 账号或密码错误 ”，该异常只做INFO级别的日志记录 @see WebMvcConfigurer
 */
class ServiceException : RuntimeException {
    constructor() {}

    constructor(message: String?) : super(message) {}

    constructor(message: String?, cause: Throwable) : super(message, cause) {}
}
