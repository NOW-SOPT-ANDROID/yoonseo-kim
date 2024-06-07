package com.sopt.now.data.repository

import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.dto.request.RequestSignUpDto
import com.sopt.now.data.service.AuthService

class AuthRepository(private val authService: AuthService) {

    suspend fun login(request: RequestLoginDto): Result<String> {
        return runCatching {
            val response = authService.login(request)
            val userId = response.headers()["location"]
            userId ?: throw IllegalStateException("User ID not found in response headers")
        }
    }

    suspend fun signUp(request: RequestSignUpDto): Result<String> {
        return runCatching {
            val response = authService.signUp(request)
            response.message
        }
    }
}