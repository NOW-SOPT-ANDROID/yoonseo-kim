package com.sopt.now.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.data.ServicePool
import com.sopt.now.data.dto.response.ResponseUserInfoDto
import com.sopt.now.presentation.user.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo> = _userInfo
    fun getUserInfo(userId: Int) {
        ServicePool.userService.getUserInfo(userId).enqueue(object : Callback<ResponseUserInfoDto> {
            override fun onResponse(
                call: Call<ResponseUserInfoDto>,
                response: Response<ResponseUserInfoDto>,
            ) {
                if (response.isSuccessful) {
                    response.body()?.data?.let {
                        val userInfo = UserInfo(it.authenticationId, it.nickname, it.phone)
                        _userInfo.value = userInfo
                    }
                } else {
                    Log.e("Home", "사용자 정보 불러오기 실패")
                }
            }

            override fun onFailure(call: Call<ResponseUserInfoDto>, t: Throwable) {
                Log.e("Home", "서버 통신 오류")
            }
        })
    }
}