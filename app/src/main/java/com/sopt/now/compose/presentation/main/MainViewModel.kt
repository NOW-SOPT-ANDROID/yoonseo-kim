package com.sopt.now.compose.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.core.view.UiState
import com.sopt.now.compose.data.ServicePool
import com.sopt.now.compose.data.dto.response.ResponseUserInfoDto
import com.sopt.now.compose.presentation.user.UserInfo
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val userService by lazy { ServicePool.userService }

    private val _userInfo = MutableLiveData<UserInfo?>()
    val userInfo: LiveData<UserInfo?> get() = _userInfo

    fun getUserInfo(userId: Int) {
        viewModelScope.launch {
            runCatching {
                userService.getUserInfo(userId)
            }.onSuccess {
                it.body()?.data?.let { userInfoDto ->
                    _userInfo.value = UserInfo(
                        nickname = userInfoDto.nickname,
                        phone = userInfoDto.phone,
                        authenticationId = userInfoDto.authenticationId
                    )
                }
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