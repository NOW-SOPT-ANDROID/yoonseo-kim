package com.sopt.now.compose.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.sopt.now.compose.presentation.friend.FriendItem
import com.sopt.now.compose.presentation.user.UserItem
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sopt.now.compose.core.base.factory.BaseViewModelFactory
import com.sopt.now.compose.core.base.factory.FriendViewModelFactory
import com.sopt.now.compose.core.base.factory.UserViewModelFactory
import com.sopt.now.compose.data.ServicePool
import com.sopt.now.compose.data.repoImpl.FriendRepositoryImpl
import com.sopt.now.compose.data.repoImpl.UserRepositoryImpl
import com.sopt.now.compose.data.repository.FriendRepository
import com.sopt.now.compose.data.repository.UserRepository
import com.sopt.now.compose.presentation.main.MainViewModel
import com.sopt.now.compose.presentation.user.UserInfo

@SuppressLint("UnrememberedMutableState")
@Composable
fun HomeScreen(userId: Int) {

    val userRepository by remember { mutableStateOf(UserRepositoryImpl(ServicePool.userService)) }
    val userViewModelFactory by remember { mutableStateOf(BaseViewModelFactory(userRepository = userRepository)) }

    val friendRepository by remember { mutableStateOf(FriendRepositoryImpl(ServicePool.friendService)) }
    val friendViewModelFactory by remember { mutableStateOf(BaseViewModelFactory(friendRepository = friendRepository)) }

    val mainViewModel: MainViewModel = viewModel(factory = userViewModelFactory)
    val homeViewModel: HomeViewModel = viewModel(factory = friendViewModelFactory)

    val userInfo by mainViewModel.userInfo.observeAsState()
    val friendList by homeViewModel.friendList.observeAsState(emptyList())

    val userInfoState = remember { mutableStateOf<UserInfo?>(null) }

    mainViewModel.getUserInfo(userId)

    userInfo?.let {
        userInfoState.value = it
        homeViewModel.getFriendList()
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
