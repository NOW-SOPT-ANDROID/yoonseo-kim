package com.sopt.now.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.ServicePool
import com.sopt.now.data.dto.response.ResponseUserInfoDto
import com.sopt.now.presentation.user.UserInfo
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val userService by lazy { ServicePool.userService }

    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo> = _userInfo
    fun getUserInfo(userId: Int) {
        viewModelScope.launch {
            runCatching {
                userService.getUserInfo(userId)
            }.onSuccess { response ->
                _userInfo.value = response.data
            }.onFailure {
                if (it is HttpException) {
                    Log.d("UserInfo", "서버 통신 오류")
                } else {
                    Log.d("UserInfo", "사용자 정보 불러오기 실패")
                }
            }
        }
    }
}