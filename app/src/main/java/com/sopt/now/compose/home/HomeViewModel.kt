package com.sopt.now.compose.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.sopt.now.compose.R
import com.sopt.now.compose.friend.Friend
import com.sopt.now.compose.user.UserInfo


class HomeViewModel : ViewModel() {

    val userList = mutableStateListOf (
        UserInfo (
            authenticationId = "yoonseo",
            nickname = "yoon",
            phone = "010-1234-1234"
        )
    )

    val friendList = mutableStateListOf(
        Friend(
            profileImage = R.drawable.carrot,
            name = "jihyun",
            selfDescription = "안녕하세용",
        ),
        Friend(
            profileImage = R.drawable.heart,
            name = "gaeul",
            selfDescription = "저희 2조는",
        ),
        Friend(
            profileImage = R.drawable.soccer,
            name = "seokchan",
            selfDescription = "석류톡톡이에용",
        ),
        Friend(
            profileImage = R.drawable.carrot,
            name = "jihyun",
            selfDescription = "안녕하세용",
        ),
        Friend(
            profileImage = R.drawable.heart,
            name = "gaeul",
            selfDescription = "저희 2조는",
        ),
        Friend(
            profileImage = R.drawable.soccer,
            name = "seokchan",
            selfDescription = "석류톡톡이에용",
        ),
        Friend(
            profileImage = R.drawable.carrot,
            name = "jihyun",
            selfDescription = "안녕하세용",
        ),
        Friend(
            profileImage = R.drawable.heart,
            name = "gaeul",
            selfDescription = "저희 2조는",
        ),
        Friend(
            profileImage = R.drawable.soccer,
            name = "seokchan",
            selfDescription = "석류톡톡이에용",
        ),
        Friend(
            profileImage = R.drawable.carrot,
            name = "jihyun",
            selfDescription = "안녕하세용",
        ),
        Friend(
            profileImage = R.drawable.heart,
            name = "gaeul",
            selfDescription = "저희 2조는",
        ),
        Friend(
            profileImage = R.drawable.soccer,
            name = "seokchan",
            selfDescription = "석류톡톡이에용",
        ),
        Friend(
            profileImage = R.drawable.carrot,
            name = "jihyun",
            selfDescription = "안녕하세용",
        ),
        Friend(
            profileImage = R.drawable.heart,
            name = "gaeul",
            selfDescription = "저희 2조는",
        ),
        Friend(
            profileImage = R.drawable.soccer,
            name = "seokchan",
            selfDescription = "석류톡톡이에용",
        )
    )
}



