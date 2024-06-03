package com.sopt.now.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class UserInfo(
    val authenticationId: String,
    val nickname: String,
    val phone: String
)