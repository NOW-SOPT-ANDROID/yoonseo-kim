package com.sopt.now.compose.presentation.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.sopt.now.compose.presentation.friend.FriendItem
import com.sopt.now.compose.presentation.user.UserItem
import androidx.compose.ui.Modifier
import com.sopt.now.compose.data.ServicePool
import com.sopt.now.compose.data.dto.response.ResponseFriendDto
import com.sopt.now.compose.presentation.user.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("UnrememberedMutableState")
@Composable
fun HomeScreen() {

    val userList = mutableStateListOf (
        UserInfo (
            authenticationId = "yoonseo",
            nickname = "yoon",
            phone = "010-1234-1234"
        )
    )

    val friendList = remember { mutableStateListOf<ResponseFriendDto.Data>() }

    LaunchedEffect(key1 = true) {
        getFriendList(friendList)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(userList) { user ->
            UserItem(user)
        }
        items(friendList) { friend ->
            FriendItem(friend)
        }
    }
}

fun getFriendList(friendList: MutableList<ResponseFriendDto.Data>) {
    ServicePool.friendService.getFriendList(2).enqueue(object : Callback<ResponseFriendDto> {
        override fun onResponse(
            call: Call<ResponseFriendDto>,
            response: Response<ResponseFriendDto>,
        ) {
            if (response.isSuccessful) {
                val data: ResponseFriendDto? = response.body()
                data?.data?.let { dataList ->
                    friendList.addAll(dataList)
                }
                Log.d("MyPage", "data: $data")
            } else {
                val error = response.errorBody()?.string() ?: response.message()
                Log.d("MyPage", "error: $error")
            }
        }

        override fun onFailure(call: Call<ResponseFriendDto>, t: Throwable) {
            Log.d("MyPage", "onFailure", t)
        }
    })
}
