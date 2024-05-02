package com.sopt.now.response

import com.sopt.now.user.UserInfo

data class ResponseUserInfoDto(
    val code: Int,
    val message: String,
    val data: UserInfo
)
