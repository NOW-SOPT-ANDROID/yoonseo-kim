package com.sopt.now.data.datasource

import com.sopt.now.data.dto.response.ResponseFriendDto

interface FriendDataSource {
    suspend fun getFriends(page: Int): ResponseFriendDto
}