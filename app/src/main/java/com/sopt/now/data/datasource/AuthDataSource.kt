package com.sopt.now.data.datasource

import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.dto.request.RequestSignUpDto
import com.sopt.now.data.dto.response.BaseResponse
import retrofit2.Response

interface AuthDataSource {
    suspend fun login(request: RequestLoginDto): Response<BaseResponse<Unit>>

    suspend fun signUp(request: RequestSignUpDto): BaseResponse<Unit>
}