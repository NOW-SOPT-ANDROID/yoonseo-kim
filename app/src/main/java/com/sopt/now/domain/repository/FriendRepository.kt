package com.sopt.now.domain.repository

import com.sopt.now.domain.entity.FriendEntity

interface FriendRepository {
    suspend fun getFriends(page: Int): Result<List<FriendEntity>>
}