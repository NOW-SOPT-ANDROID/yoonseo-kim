package com.sopt.now.compose.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.compose.R
import com.sopt.now.compose.ServicePool
import com.sopt.now.compose.friend.Friend
import com.sopt.now.compose.response.ResponseFriendDto
import com.sopt.now.compose.user.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel : ViewModel() {
//    private val _userList = MutableLiveData(
//        listOf(
//            UserInfo(
//                authenticationId = "yoonseo",
//                nickname = "yoon",
//                phone = "010-1234-1234"
//            )
//        )
//    )
//    val userList: LiveData<List<UserInfo>> = _userList
//
//    private val _friendList = MutableLiveData<List<ResponseFriendDto.Data>>(emptyList())
//    val friendList: LiveData<List<ResponseFriendDto.Data>> = _friendList
//
//    init {
//        getFriendList()
//    }
//
//    private fun getFriendList() {
//        ServicePool.friendService.getFriendList(2).enqueue(object : Callback<ResponseFriendDto> {
//            override fun onResponse(
//                call: Call<ResponseFriendDto>,
//                response: Response<ResponseFriendDto>,
//            ) {
//                if (response.isSuccessful) {
//                    val data: ResponseFriendDto? = response.body()
//                    data?.data?.let { dataList ->
//                        _friendList.postValue(dataList)
//                    }
//                    Log.d("HomeViewModel", "data: $data")
//                } else {
//                    val error = response.errorBody()?.string() ?: response.message()
//                    Log.d("HomeViewModel", "error: $error")
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseFriendDto>, t: Throwable) {
//                Log.d("HomeViewModel", "onFailure", t)
//            }
//        })
//    }
}



