package com.sopt.now.data.datasourceImpl

import com.sopt.now.data.datasource.AuthDataSource
import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.dto.request.RequestSignUpDto
import com.sopt.now.data.dto.response.BaseResponse
import com.sopt.now.data.service.AuthService
import retrofit2.Response

class AuthDataSourceImpl(
    private val authService: AuthService
) : AuthDataSource {

    override suspend fun login(request: RequestLoginDto): Response<BaseResponse<Unit>> =
        authService.login(request)

    override suspend fun signUp(request: RequestSignUpDto): BaseResponse<Unit> =
        authService.signUp(request)

}