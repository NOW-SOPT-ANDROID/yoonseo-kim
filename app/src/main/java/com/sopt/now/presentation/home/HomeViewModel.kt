package com.sopt.now.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.ServicePool
import com.sopt.now.presentation.friend.Friend
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeViewModel : ViewModel() {

    private val friendService = ServicePool.friendService
    private val _friends = MutableLiveData<List<Friend>>()
    val friends: LiveData<List<Friend>> = _friends

    fun getFriends() {
        viewModelScope.launch {
            runCatching {
                friendService.getFriends(PAGE)
            }.onSuccess { response ->
                _friends.postValue(response.data)
            }.onFailure {
                if (it is HttpException) {
                    Log.e("FriendList", "서버 통신 오류")
                } else {
                    Log.e("FriendList", "친구 정보 불러오기 실패")
                }
            }
        }
    }

    companion object {
        const val PAGE = 2
    }
}