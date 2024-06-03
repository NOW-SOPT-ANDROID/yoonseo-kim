package com.sopt.now.compose.presentation.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.data.ServicePool
import com.sopt.now.compose.presentation.main.MainActivity
import com.sopt.now.compose.data.dto.request.RequestLoginDto
import com.sopt.now.compose.core.view.UiState
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel : ViewModel() {

    private val authService by lazy { ServicePool.authService }
    private val _loginState = MutableLiveData<UiState>()
    val loginState: LiveData<UiState> get() = _loginState

    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> get() = _userId

    fun login(request: RequestLoginDto) {
        viewModelScope.launch {
            runCatching {
                authService.login(request)
            }.onSuccess {
                val userId = it.headers()["location"]
                _userId.value = userId.toString()
                _loginState.value = UiState(true, "로그인 성공 !")
            }.onFailure {
                if (it is HttpException) {
                    _loginState.value = UiState(false, it.message())
                } else {
                    _loginState.value = UiState(false, "로그인 실패")
                }
            }
        }
    }
}