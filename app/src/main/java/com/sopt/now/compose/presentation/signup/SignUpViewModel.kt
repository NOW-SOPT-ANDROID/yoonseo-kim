package com.sopt.now.compose.presentation.signup

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.compose.data.ServicePool
import com.sopt.now.compose.presentation.login.LoginActivity
import com.sopt.now.compose.data.dto.request.RequestSignUpDto
import com.sopt.now.compose.data.dto.response.ResponseSignUpDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {
    private val authService by lazy { ServicePool.authService }
    private val _signUpState = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState> get() = _signUpState

    fun signUp(context: Context, id: String, password: String, nickname: String, phone: String) {
        val signUpRequest = RequestSignUpDto(authenticationId = id, password = password, nickname = nickname, phone = phone)
        authService.signUp(signUpRequest).enqueue(object : Callback<ResponseSignUpDto> {
            override fun onResponse(call: Call<ResponseSignUpDto>, response: Response<ResponseSignUpDto>) {
                if (response.isSuccessful) {
                    val data: ResponseSignUpDto? = response.body()
                    val userId = response.headers()["location"]
                    Log.d("SignUp", "data: $data, userId: $userId")
                    _signUpState.value = SignUpState(true, "회원가입 성공 !")
                    if (context is Activity) {
                        navigateToLogin(userId, context)
                    }
                } else {
                    val error = response.message()
                    _signUpState.value = SignUpState(false, "회원가입 실패: $error")
                }
            }
            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                _signUpState.value = SignUpState(false, "서버 에러 발생")
            }
        })
    }

    private fun navigateToLogin(userId: String?, activity: Activity) {
        val intent = Intent(activity, LoginActivity::class.java).apply {
            putExtra("userId", userId)
        }
        activity.startActivity(intent)
        activity.finish()
    }
}