package com.sopt.now.domain.repository

import com.sopt.now.presentation.friend.Friend

interface FriendRepository {
    suspend fun getFriends(page: Int): Result<List<Friend>>
}