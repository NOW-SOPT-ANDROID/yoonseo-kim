package com.sopt.now.data.repoImpl

import com.sopt.now.data.ServicePool.userService
import com.sopt.now.data.datasource.UserDataSource
import com.sopt.now.data.dto.response.toUserEntity
import com.sopt.now.domain.repository.UserRepository
import com.sopt.now.data.service.UserService
import com.sopt.now.domain.entity.UserEntity

class UserRepositoryImpl(
    private val userDataSource: UserDataSource
) : UserRepository {

    override suspend fun getUserInfo(): Result<UserEntity> = runCatching {
        userDataSource.getUserInfo().data?.toUserEntity() ?: throw Exception("data is null")
    }
}