package com.sopt.now.domain.repository

import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.dto.request.RequestSignUpDto
import com.sopt.now.domain.entity.BaseResponseEntity
import com.sopt.now.domain.entity.UserEntity

interface AuthRepository {
    suspend fun login(id: String, password: String): Result<Int?>
    suspend fun signUp(userEntity: UserEntity): Result<BaseResponseEntity>
}