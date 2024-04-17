package com.sopt.now.compose.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.sopt.now.compose.friend.Friend
import com.sopt.now.compose.friend.FriendItem
import com.sopt.now.compose.user.UserInfo
import com.sopt.now.compose.user.UserItem
import androidx.compose.ui.Modifier


val userList = listOf<UserInfo>(
    UserInfo(
        id = "yskimm",
        password = "ysysysys",
        nickname = "yoonseo",
        mbti = "ESFJ"
    )
)

val friendList = listOf<Friend>(
    Friend(
        profileImage = Icons.Filled.Person,
        name = "jihyun",
        selfDescription = "안녕하세용",
    ),
    Friend(
        profileImage = Icons.Filled.Person,
        name = "gaeul",
        selfDescription = "저희 2조는",
    ),
    Friend(
        profileImage = Icons.Filled.Person,
        name = "seokchan",
        selfDescription = "석류톡톡이에용",
    ),
    Friend(
        profileImage = Icons.Filled.Person,
        name = "jihyun",
        selfDescription = "안녕하세용",
    ),
    Friend(
        profileImage = Icons.Filled.Person,
        name = "gaeul",
        selfDescription = "저희 2조는",
    ),
    Friend(
        profileImage = Icons.Filled.Person,
        name = "seokchan",
        selfDescription = "석류톡톡이에용",
    ),
    Friend(
        profileImage = Icons.Filled.Person,
        name = "jihyun",
        selfDescription = "안녕하세용",
    ),
    Friend(
        profileImage = Icons.Filled.Person,
        name = "gaeul",
        selfDescription = "저희 2조는",
    ),
    Friend(
        profileImage = Icons.Filled.Person,
        name = "seokchan",
        selfDescription = "석류톡톡이에용",
    ),
    Friend(
        profileImage = Icons.Filled.Person,
        name = "jihyun",
        selfDescription = "안녕하세용",
    ),
    Friend(
        profileImage = Icons.Filled.Person,
        name = "gaeul",
        selfDescription = "저희 2조는",
    ),
    Friend(
        profileImage = Icons.Filled.Person,
        name = "seokchan",
        selfDescription = "석류톡톡이에용",
    ),
    Friend(
        profileImage = Icons.Filled.Person,
        name = "jihyun",
        selfDescription = "안녕하세용",
    ),
    Friend(
        profileImage = Icons.Filled.Person,
        name = "gaeul",
        selfDescription = "저희 2조는",
    ),
    Friend(
        profileImage = Icons.Filled.Person,
        name = "seokchan",
        selfDescription = "석류톡톡이에용",
    ),
)

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(userList) {
                UserItem(it)
            }
            items(friendList) {
                FriendItem(it)
            }
        }
    }
}