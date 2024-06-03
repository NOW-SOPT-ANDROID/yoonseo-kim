package com.sopt.now.compose.presentation.home

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.sopt.now.compose.presentation.friend.FriendItem
import com.sopt.now.compose.presentation.user.UserItem
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sopt.now.compose.presentation.main.MainViewModel
import com.sopt.now.compose.presentation.user.UserInfo

@SuppressLint("UnrememberedMutableState")
@Composable
fun HomeScreen(context: Context, userId: Int) {

    val mainViewModel: MainViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()

    val userInfo by mainViewModel.userInfo.observeAsState()
    val friendList by homeViewModel.friendList.observeAsState(emptyList())

    val userInfoState = remember { mutableStateOf<UserInfo?>(null) }

    mainViewModel.getUserInfo(userId)

    LaunchedEffect(userInfo) {
        userInfo?.let {
            userInfoState.value = it
            homeViewModel.getFriendList(friendList)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        userInfoState.value?.let { user ->
            item {
                UserItem(user)
            }
        }
        items(friendList) { friend ->
            FriendItem(friend)
        }
    }
}
