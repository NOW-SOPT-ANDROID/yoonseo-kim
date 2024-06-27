package com.sopt.now.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.domain.repository.UserRepository
import com.sopt.now.domain.entity.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userInfo = MutableStateFlow(UserEntity())
    val userInfo: StateFlow<UserEntity> = _userInfo

    fun getUserInfo(userId: Int) {
        viewModelScope.launch {
            userRepository.getUserInfo()
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