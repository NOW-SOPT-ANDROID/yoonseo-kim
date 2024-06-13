package com.sopt.now.data.repository

import com.sopt.now.presentation.user.UserInfo

interface UserRepository {
    suspend fun getUserInfo(userId: Int): Result<UserInfo>
}