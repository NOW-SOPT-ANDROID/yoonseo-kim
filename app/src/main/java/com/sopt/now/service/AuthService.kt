package com.sopt.now.service

import com.sopt.now.request.RequestLoginDto
import com.sopt.now.request.RequestSignUpDto
import com.sopt.now.response.ResponseLoginDto
import com.sopt.now.response.ResponseSignUpDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    // 코루틴 서버 통신 - DTO 자체를 리턴 타입으로
    @POST("member/join")
    suspend fun signUp(
        @Body request: RequestSignUpDto,
    ): ResponseSignUpDto

    @POST("member/login")
    fun login(
        @Body request: RequestLoginDto,
    ): Call<ResponseLoginDto>
}