package com.sopt.now.data.datasource

import com.sopt.now.data.dto.response.BaseResponse
import com.sopt.now.data.dto.response.ResponseMemberInfo

interface UserDataSource {
    suspend fun getUserInfo(): BaseResponse<ResponseMemberInfo>
}