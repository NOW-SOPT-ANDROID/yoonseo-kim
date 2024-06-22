package com.sopt.now.data.repoImpl

import com.sopt.now.domain.repository.UserRepository
import com.sopt.now.data.service.UserService
import com.sopt.now.domain.entity.UserInfo

class UserRepositoryImpl(private val userService: UserService) : UserRepository {

    override suspend fun getUserInfo(userId: Int): Result<UserInfo> {
        return runCatching {
            val response = userService.getUserInfo(userId)
            response.data
        }
    }
}