package com.sopt.now.data.repository

import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.dto.request.RequestSignUpDto

interface AuthRepository {
    suspend fun login(request: RequestLoginDto): Result<String>
    suspend fun signUp(request: RequestSignUpDto): Result<String>
}