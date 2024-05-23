package com.sopt.now.compose.presentation.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.compose.data.ServicePool
import com.sopt.now.compose.presentation.main.MainActivity
import com.sopt.now.compose.data.dto.request.RequestLoginDto
import com.sopt.now.compose.data.dto.response.ResponseLoginDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val authService by lazy { ServicePool.authService }

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> get() = _loginState

    fun login(context: Context, id: String, password: String) {
        val loginRequest = RequestLoginDto(authenticationId = id, password = password)
        authService.login(loginRequest).enqueue(object : Callback<ResponseLoginDto> {
            override fun onResponse(call: Call<ResponseLoginDto>, response: Response<ResponseLoginDto>) {
                if (response.isSuccessful) {
                    val data: ResponseLoginDto? = response.body()
                    val userId = response.headers()["location"]
                    //Toast.makeText(context, "회원가입 성공 유저의 ID는 $userId 입니둥", Toast.LENGTH_SHORT).show()
                    Log.d("Login", "data: $data, userId: $userId")
                    _loginState.value = LoginState(true, "로그인 성공 !")
                    if (context is Activity) {
                        navigateToMain(userId, context)
                    }
                } else {
                    val error = response.message()
                    Toast.makeText(context, "로그인 실패: $error", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                Toast.makeText(context, "서버 에러 발생", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun navigateToMain(userId: String?, activity: Activity) {
        val intent = Intent(activity, MainActivity::class.java).apply {
            putExtra("userId", userId)
        }
        activity.startActivity(intent)
        activity.finish()
    }

}