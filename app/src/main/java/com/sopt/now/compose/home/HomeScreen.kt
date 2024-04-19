package com.sopt.now.compose.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.sopt.now.compose.friend.FriendItem
import com.sopt.now.compose.user.UserItem
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    //ViewModel의 생명주기를 Composable의 생명주기와 연결 (적절한 ViewModel 인스턴스 제공)
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(homeViewModel.userList) { user ->
                UserItem(user)
            }
            items(homeViewModel.friendList) { friend ->
                FriendItem(friend)
            }
        }
    }
}