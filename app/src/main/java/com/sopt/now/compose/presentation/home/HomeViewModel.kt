package com.sopt.now.compose.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.data.ServicePool
import com.sopt.now.compose.data.dto.response.ResponseFriendDto
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeViewModel : ViewModel() {

    private val friendService by lazy { ServicePool.friendService }
    private val _friendList = MutableLiveData<List<ResponseFriendDto.Data>>()
    val friendList: LiveData<List<ResponseFriendDto.Data>> get() = _friendList

    fun getFriendList() {
        viewModelScope.launch {
            runCatching {
                friendService.getFriendList(PAGE)
            }.onSuccess {
                _friendList.postValue(it.body()?.data ?: emptyList())
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
