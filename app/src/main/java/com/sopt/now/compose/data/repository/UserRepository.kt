package com.sopt.now.compose.data.repository

import com.sopt.now.compose.data.service.UserService
import com.sopt.now.compose.presentation.user.UserInfo

class UserRepository(private val userService: UserService) {
    suspend fun getUserInfo(userId: Int): Result<UserInfo> {
        return runCatching {
            val response = userService.getUserInfo(userId)
            response.data
        }
    }
}