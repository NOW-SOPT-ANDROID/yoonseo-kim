package com.sopt.now.compose

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    var id : String = "",
    var password : String = "",
    var nickname : String = "",
    var mbti : String = ""
) : Parcelable