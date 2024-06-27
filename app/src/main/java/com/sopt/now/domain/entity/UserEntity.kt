package com.sopt.now.domain.entity

import com.sopt.now.data.dto.request.RequestSignUpDto
import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(
    val id: String = "",
    val nickname: String = "",
    val phone: String = ""
) {
    fun toSignUpDto() = RequestSignUpDto(
        authenticationId = id,
        nickname = nickname,
        phone = phone
    )
}