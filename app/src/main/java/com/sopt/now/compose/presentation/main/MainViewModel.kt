package com.sopt.now.compose.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.data.ServicePool
import com.sopt.now.compose.data.repository.UserRepository
import com.sopt.now.compose.presentation.user.UserInfo
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    //private val userService by lazy { ServicePool.userService }

    private val _userInfo = MutableLiveData<UserInfo?>()
    val userInfo: LiveData<UserInfo?> get() = _userInfo

    fun getUserInfo(userId: Int) {
        viewModelScope.launch {
            userRepository.getUserInfo(userId)
                .onSuccess { userInfo ->
                    _userInfo.value = userInfo
                }
                .onFailure {
                    if (it is HttpException) {
                        Log.d("UserInfo", "서버 통신 오류")
                    } else {
                        Log.d("UserInfo", "사용자 정보 불러오기 실패")
                    }
                }
        }
    }
}