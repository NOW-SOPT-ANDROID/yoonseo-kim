package com.sopt.now.compose.data.repository

import com.sopt.now.compose.data.service.FriendService
import com.sopt.now.compose.presentation.friend.Friend

class FriendRepository(private val friendService: FriendService) {
    suspend fun getFriendList(page: Int): Result<List<Friend>> {
        return runCatching {
            val response = friendService.getFriendList(page)
            response.data
        }
    }
}