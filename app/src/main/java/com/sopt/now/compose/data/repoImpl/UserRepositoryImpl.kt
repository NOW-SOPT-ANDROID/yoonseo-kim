package com.sopt.now.compose.data.repoImpl

import com.sopt.now.compose.data.repository.UserRepository
import com.sopt.now.compose.data.service.UserService
import com.sopt.now.compose.presentation.user.UserInfo

class UserRepositoryImpl(private val userService: UserService) : UserRepository {

    override suspend fun getUserInfo(userId: Int): Result<UserInfo> {
        return runCatching {
            val response = userService.getUserInfo(userId)
            response.data
        }
    }
}