package com.sopt.now.compose.service

import com.sopt.now.compose.request.RequestLoginDto
import com.sopt.now.compose.request.RequestSignUpDto
import com.sopt.now.compose.response.ResponseLoginDto
import com.sopt.now.compose.response.ResponseSignUpDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("member/join")
    fun signUp(
        @Body request: RequestSignUpDto,
    ): Call<ResponseSignUpDto>

    @POST("member/login")
    fun login(
        @Body request: RequestLoginDto,
    ): Call<ResponseLoginDto>
}