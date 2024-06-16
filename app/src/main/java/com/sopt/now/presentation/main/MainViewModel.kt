package com.sopt.now.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.repository.UserRepository
import com.sopt.now.presentation.user.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userInfo = MutableStateFlow(UserInfo())
    val userInfo: StateFlow<UserInfo> = _userInfo

    fun getUserInfo(userId: Int) {
        viewModelScope.launch {
            userRepository.getUserInfo(userId)
                .onSuccess { userInfo ->
                    _userInfo.value = userInfo
                }
                .onFailure {
                    if (it is HttpException) {
                        Log.e("UserInfo", "서버 통신 오류")
                    } else {
                        Log.e("UserInfo", "사용자 정보 불러오기 실패")
                    }
                }
        }
    }
}