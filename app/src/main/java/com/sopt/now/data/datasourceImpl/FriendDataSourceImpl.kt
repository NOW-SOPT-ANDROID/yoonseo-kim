package com.sopt.now.data.datasourceImpl

import com.sopt.now.data.datasource.FriendDataSource
import com.sopt.now.data.dto.response.ResponseFriendDto
import com.sopt.now.data.service.FriendService

class FriendDataSourceImpl(
    private val friendService: FriendService
) : FriendDataSource {

    override suspend fun getFriends(page: Int): ResponseFriendDto =
        friendService.getFriends(page)

}