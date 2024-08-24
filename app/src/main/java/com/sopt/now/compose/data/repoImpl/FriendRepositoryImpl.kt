package com.sopt.now.compose.data.repoImpl

import com.sopt.now.compose.data.repository.FriendRepository
import com.sopt.now.compose.data.service.FriendService
import com.sopt.now.compose.presentation.friend.Friend

class FriendRepositoryImpl(private val friendService: FriendService) : FriendRepository {

    override suspend fun getFriendList(page: Int): Result<List<Friend>> = runCatching {
        val response = friendService.getFriendList(page)
        response.data
    }
}