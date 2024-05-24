package com.sopt.now.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.data.ServicePool
import com.sopt.now.data.dto.response.ResponseFriendDto
import com.sopt.now.presentation.friend.Friend
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val friendService by lazy { ServicePool.friendService }
    private val _friends = MutableLiveData<List<Friend>>()
    val friends: LiveData<List<Friend>> = _friends

    fun getFriends() {
        friendService.getFriends(2).enqueue(object : Callback<ResponseFriendDto> {
            override fun onResponse(
                call: Call<ResponseFriendDto>,
                response: Response<ResponseFriendDto>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data?.map {
                        Friend(it.id, it.email, it.firstName, it.lastName, it.avatar)
                    } ?: listOf()
                    _friends.value = data
                } else {
                    Log.e("Home", "친구 목록 불러오기 실패")
                }
            }

            override fun onFailure(call: Call<ResponseFriendDto>, t: Throwable) {
                Log.e("Home", "서버 통신 오류")
            }
        })
    }
}