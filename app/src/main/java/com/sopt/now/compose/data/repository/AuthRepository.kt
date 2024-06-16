package com.sopt.now.compose.data.repository

import com.sopt.now.compose.data.dto.request.RequestLoginDto
import com.sopt.now.compose.data.dto.request.RequestSignUpDto

interface AuthRepository {
    suspend fun login(request: RequestLoginDto): Result<String>
    suspend fun signUp(request: RequestSignUpDto): Result<String>
}