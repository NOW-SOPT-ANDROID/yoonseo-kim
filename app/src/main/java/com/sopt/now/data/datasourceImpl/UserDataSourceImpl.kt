package com.sopt.now.data.datasourceImpl

import com.sopt.now.data.datasource.UserDataSource
import com.sopt.now.data.dto.response.BaseResponse
import com.sopt.now.data.dto.response.ResponseMemberInfo
import com.sopt.now.data.service.UserService
import com.sopt.now.domain.entity.UserEntity

class UserDataSourceImpl(
    private val userService: UserService
) : UserDataSource {

    override suspend fun getUserInfo(): BaseResponse<ResponseMemberInfo> =
        userService.getUserInfo()

}