package com.sopt.now.data.repoImpl

import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.dto.request.RequestSignUpDto
import com.sopt.now.data.repository.AuthRepository
import com.sopt.now.data.service.AuthService

class AuthRepositoryImpl(private val authService: AuthService) : AuthRepository {

    override suspend fun login(request: RequestLoginDto): Result<String> = runCatching {
        val response = authService.login(request)
        val userId = response.headers()["location"]
        userId ?: throw IllegalStateException("User ID not found")
    }

    override suspend fun signUp(request: RequestSignUpDto): Result<String> = runCatching {
        val response = authService.signUp(request)
        response.message
    }
}