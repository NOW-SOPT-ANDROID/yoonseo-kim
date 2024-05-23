package com.sopt.now.compose.presentation.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    val authenticationId: String,
    val nickname: String,
    val phone: String
) : Parcelable