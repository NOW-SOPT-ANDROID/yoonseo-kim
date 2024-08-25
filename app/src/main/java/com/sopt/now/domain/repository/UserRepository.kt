package com.sopt.now.domain.repository

import com.sopt.now.domain.entity.UserEntity

interface UserRepository {
    suspend fun getUserInfo(): Result<UserEntity>
}