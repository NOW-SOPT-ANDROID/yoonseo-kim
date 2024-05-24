package com.sopt.now.compose.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.data.ServicePool
import com.sopt.now.compose.data.dto.response.ResponseUserInfoDto
import com.sopt.now.compose.presentation.user.UserInfo
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val userService by lazy { ServicePool.userService }

    private val _userInfo = MutableLiveData<UserInfo?>()
    val userInfo: LiveData<UserInfo?> get() = _userInfo

    fun getUserInfo(userId: Int) {
        viewModelScope.launch {
            userService.getUserInfo(userId).enqueue(object : Callback<ResponseUserInfoDto> {
                override fun onResponse(
                    call: Call<ResponseUserInfoDto>,
                    response: Response<ResponseUserInfoDto>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.data?.let { userInfoDto ->
                            _userInfo.value = UserInfo(
                                nickname = userInfoDto.nickname,
                                phone = userInfoDto.phone,
                                authenticationId = userInfoDto.authenticationId
                            )
                        }
                    } else {
                        Log.d("MyPage", "Error: ${response.errorBody()?.string() ?: response.message()}")
                    }
                }

                override fun onFailure(call: Call<ResponseUserInfoDto>, t: Throwable) {
                    Log.d("MyPage", "onFailure", t)
                }
            })
        }
    }

//    companion object {
//        const val USER_ID = 51
//    }
}