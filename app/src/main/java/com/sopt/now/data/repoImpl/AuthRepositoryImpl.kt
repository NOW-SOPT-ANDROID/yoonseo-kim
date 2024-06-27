package com.sopt.now.data.repoImpl

import com.sopt.now.data.ServicePool.authService
import com.sopt.now.data.datasource.AuthDataSource
import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.dto.request.RequestSignUpDto
import com.sopt.now.domain.entity.BaseResponseEntity
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource
) : AuthRepository {

    override suspend fun login(id: String, password: String): Result<Int?> = runCatching {
        val response = authDataSource.login(RequestLoginDto(id, password))
        val userId = response.headers()["location"]?.toInt()
        userId ?: throw IllegalStateException("User ID not found")
    }

    override suspend fun signUp(userEntity: UserEntity): Result<BaseResponseEntity> = runCatching {
        val response = authDataSource.signUp(userEntity.toSignUpDto())
        BaseResponseEntity(
            code = response.code,
            message = response.message
        )
    }
}