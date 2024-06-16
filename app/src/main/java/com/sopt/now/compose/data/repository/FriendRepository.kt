package com.sopt.now.compose.data.repository

import com.sopt.now.compose.presentation.friend.Friend

interface FriendRepository {
    suspend fun getFriendList(page: Int): Result<List<Friend>>
}