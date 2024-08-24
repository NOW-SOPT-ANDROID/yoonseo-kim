package com.sopt.now.compose.data.repository

import com.sopt.now.compose.presentation.user.UserInfo

interface UserRepository {
    suspend fun getUserInfo(userId: Int): Result<UserInfo>
}