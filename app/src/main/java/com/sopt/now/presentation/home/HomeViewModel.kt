package com.sopt.now.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.domain.repository.FriendRepository
import com.sopt.now.presentation.friend.Friend
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeViewModel(private val friendRepository: FriendRepository) : ViewModel() {

    private val _friends = MutableStateFlow<List<Friend>>(emptyList())
    val friends: StateFlow<List<Friend>> = _friends

    fun getFriends() {
        viewModelScope.launch {
            friendRepository.getFriends(PAGE)
                .onSuccess { friends ->
                    _friends.value = friends
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