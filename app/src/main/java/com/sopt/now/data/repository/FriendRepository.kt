package com.sopt.now.data.repository

import com.sopt.now.data.service.FriendService
import com.sopt.now.presentation.friend.Friend

class FriendRepository(private val friendService: FriendService) {
    suspend fun getFriends(page: Int): Result<List<Friend>> {
        return runCatching {
            val response = friendService.getFriends(page)
            response.data ?: throw IllegalStateException("No data available")
        }
    }
}