package com.sopt.now.compose.data.service

import com.sopt.now.compose.data.dto.response.ResponseFriendDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FriendService {
    @GET("users")
    suspend fun getFriendList(
        @Query("page") page: Int
    ) : Response<ResponseFriendDto>
}