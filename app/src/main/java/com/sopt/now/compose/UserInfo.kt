package com.sopt.now.compose

import java.io.Serializable

data class UserInfo(
    var id : String = "",
    var password : String = "",
    var nickname : String = "",
    var mbti : String = ""
) : Serializable