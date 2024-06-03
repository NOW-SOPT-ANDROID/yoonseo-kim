package com.sopt.now.service

import com.sopt.now.response.ResponseFriendDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FriendService {
    @GET("users")
    fun getFriends(
        @Query("page") page: Int
    ) : Call<ResponseFriendDto>
}