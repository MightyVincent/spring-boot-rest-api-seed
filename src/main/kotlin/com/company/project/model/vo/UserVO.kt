package com.company.project.model.vo

import com.company.project.model.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.rest.core.config.Projection

@Projection(types = [User::class])
interface UserVO {

    @Value("#{target.name} #{target.phone}")
    fun getInfo(): String

}
