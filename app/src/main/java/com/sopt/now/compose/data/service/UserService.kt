package com.sopt.now.compose.data.service

import com.sopt.now.compose.data.dto.response.ResponseUserInfoDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface UserService {
    @GET("member/info")
    fun getUserInfo(
        @Header("memberId") userId: Int
    ): Call<ResponseUserInfoDto>
}