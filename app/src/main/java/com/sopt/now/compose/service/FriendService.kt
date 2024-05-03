package com.sopt.now.compose.service

import com.sopt.now.compose.response.ResponseFriendDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FriendService {
    @GET("users")
    fun getFriendList(
        @Query("page") page: Int
    ) : Call<ResponseFriendDto>
}