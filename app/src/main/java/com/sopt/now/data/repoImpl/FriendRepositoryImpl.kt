package com.sopt.now.data.repoImpl

import com.sopt.now.domain.repository.FriendRepository
import com.sopt.now.data.service.FriendService
import com.sopt.now.domain.entity.FriendEntity

class FriendRepositoryImpl(private val friendService: FriendService) : FriendRepository {

    override suspend fun getFriends(page: Int): Result<List<FriendEntity>> = runCatching {
        val response = friendService.getFriends(page)
        response.data
    }
}