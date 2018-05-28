package com.company.project.dao

import com.company.project.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserMapper : JpaRepository<User, Long>
