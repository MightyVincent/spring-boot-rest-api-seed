package com.company.project.service

import com.company.project.core.AbstractService
import com.company.project.model.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


/**
 * Created by CodeGenerator on 2018/04/25.
 */
@Service
@Transactional
class UserServiceImpl : AbstractService<User>(), UserService {

}
