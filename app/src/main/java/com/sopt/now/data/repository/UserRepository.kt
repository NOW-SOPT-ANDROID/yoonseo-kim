package com.sopt.now.data.repository

import com.sopt.now.data.service.UserService
import com.sopt.now.presentation.user.UserInfo

class UserRepository(private val userService: UserService) {
    suspend fun getUserInfo(userId: Int): Result<UserInfo> {
        return runCatching {
            val response = userService.getUserInfo(userId)
            response.data
        }
    }
}