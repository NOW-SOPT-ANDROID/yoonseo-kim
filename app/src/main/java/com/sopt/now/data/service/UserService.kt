package com.sopt.now.data.service

import com.sopt.now.data.dto.response.BaseResponse
import com.sopt.now.data.dto.response.ResponseMemberInfo
import com.sopt.now.data.dto.response.ResponseUserInfoDto
import com.sopt.now.domain.entity.UserEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserService {
    @GET("member/info")
    suspend fun getUserInfo(): BaseResponse<ResponseMemberInfo>
}