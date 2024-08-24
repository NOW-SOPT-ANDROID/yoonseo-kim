package com.sopt.now.compose.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.data.repository.FriendRepository
import com.sopt.now.compose.presentation.friend.Friend
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeViewModel(private val friendRepository: FriendRepository) : ViewModel() {

    private val _friendList = MutableLiveData<List<Friend>>()
    val friendList: LiveData<List<Friend>> get() = _friendList

    fun getFriendList() {
        viewModelScope.launch {
            friendRepository.getFriendList(PAGE)
                .onSuccess { friends ->
                    _friendList.postValue(friends)
                }
                .onFailure {
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
