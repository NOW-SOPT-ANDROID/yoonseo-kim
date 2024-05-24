package com.sopt.now.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.ServicePool
import com.sopt.now.data.dto.request.RequestLoginDto
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel : ViewModel() {

    private val authService by lazy { ServicePool.authService }
    private val _liveData = MutableLiveData<LoginState>()
    val liveData: LiveData<LoginState> get() = _liveData

    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> get() = _userId

    fun login(request: RequestLoginDto) {
        viewModelScope.launch {
            runCatching {
                authService.login(request)
            }.onSuccess {
                val userId = it.headers()["location"]
                _userId.value = userId.toString()
                _liveData.value = LoginState(true, "로그인 성공")
            }.onFailure {
                if (it is HttpException) {
                    _liveData.value = LoginState(false, it.message())
                } else {
                    _liveData.value = LoginState(false, "로그인 실패")
                }
            }
        }
    }
}