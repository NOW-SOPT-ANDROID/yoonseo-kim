package com.sopt.now.compose.presentation.user

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val authenticationId: String,
    val nickname: String,
    val phone: String
)
