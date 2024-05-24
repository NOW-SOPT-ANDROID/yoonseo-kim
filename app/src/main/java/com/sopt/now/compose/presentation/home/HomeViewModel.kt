package com.sopt.now.compose.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.compose.data.ServicePool
import com.sopt.now.compose.data.dto.response.ResponseFriendDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val friendService by lazy { ServicePool.friendService }
    private val _friendList = MutableLiveData<List<ResponseFriendDto.Data>>()
    val friendList: LiveData<List<ResponseFriendDto.Data>> get() = _friendList

    fun getFriendList(friendList: List<ResponseFriendDto.Data>?) {
        friendService.getFriendList(2).enqueue(object : Callback<ResponseFriendDto> {
            override fun onResponse(
                call: Call<ResponseFriendDto>,
                response: Response<ResponseFriendDto>,
            ) {
                if (response.isSuccessful) {
                    val data: ResponseFriendDto? = response.body()
                    data?.data?.let { dataList ->
                        _friendList.postValue(dataList)
                    } ?: run {
                        _friendList.postValue(emptyList())
                    }
                    Log.d("HomeViewModel", "data: $data")
                } else {
                    val error = response.errorBody()?.string() ?: response.message()
                    Log.d("HomeViewModel", "error: $error")
                }
            }

            override fun onFailure(call: Call<ResponseFriendDto>, t: Throwable) {
                Log.d("HomeViewModel", "onFailure", t)
            }
        })
    }
}
