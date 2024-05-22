package com.sopt.now.presentation.login

import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.getString
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.R
import com.sopt.now.ServicePool
import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.dto.response.ResponseLoginDto
import com.sopt.now.presentation.main.MainActivity
import com.sopt.now.presentation.signup.SignUpState
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val authService by lazy { ServicePool.authService }
    private val _liveData = MutableLiveData<LoginState>()
    val liveData: LiveData<LoginState> = _liveData
    fun login(request: RequestLoginDto) {
//        val loginRequest = getLoginRequestDto()
//        ServicePool.authService.login(loginRequest).enqueue(object : Callback<ResponseLoginDto> {
//            override fun onResponse(
//                call: Call<ResponseLoginDto>,
//                response: Response<ResponseLoginDto>,
//            ) {
//                if (response.isSuccessful) {
//                    val data: ResponseLoginDto? = response.body()
//                    val userId = response.headers()["location"]
//                    Toast.makeText(
//                        this@LoginActivity,
//                        getString(R.string.success_message),
//                        Toast.LENGTH_SHORT,
//                    ).show()
//                    navigateToMain(userId)
//                } else {
//                    val error = response.message()
//                    Toast.makeText(
//                        this@LoginActivity,
//                        getString(R.string.fail_message),
//                        Toast.LENGTH_SHORT,
//                    ).show()
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
//                Toast.makeText(this@LoginActivity, getString(R.string.server_error_message), Toast.LENGTH_SHORT).show()
//            }
//        })

        viewModelScope.launch {
            runCatching {
                authService.login(request)
            }.onSuccess {
                _liveData.value = LoginState(true, it.message)
            }.onFailure {
                if (it is HttpException) {
                    _liveData.value = LoginState(false, it.message())
                } else {
                    _liveData.value = LoginState(false, "로그인이 실패")
                }
            }
        }
    }

    private fun navigateToMain(userId: String?) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("userId", userId)
        }
        startActivity(intent)
        finish()
    }
}