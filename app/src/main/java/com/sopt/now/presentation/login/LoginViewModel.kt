package com.sopt.now.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.ServicePool
import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.dto.response.ResponseLoginDto
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel : ViewModel() {

    private val authService by lazy { ServicePool.authService }
    private val _liveData = MutableLiveData<LoginState>()
    val liveData: LiveData<LoginState> get() = _liveData

    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> get() = _userId

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
                val userId = it.headers()["location"]
                _userId.value = userId.toString()
                _liveData.value = LoginState(true, "로그인에 성공하였습니다.")
            }.onFailure {
                if (it is HttpException) {
                    _liveData.value = LoginState(false, it.message())
                } else {
                    _liveData.value = LoginState(false, "로그인이 실패")
                }
            }
        }
    }
}