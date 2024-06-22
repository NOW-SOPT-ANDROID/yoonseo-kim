package com.sopt.now.domain.repository

import com.sopt.now.domain.entity.UserInfo

interface UserRepository {
    suspend fun getUserInfo(userId: Int): Result<UserInfo>
}