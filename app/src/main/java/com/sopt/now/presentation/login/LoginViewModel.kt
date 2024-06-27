package com.sopt.now.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core.view.UiState
import com.sopt.now.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginState = MutableStateFlow(UiState())
    val loginState: StateFlow<UiState> get() = _loginState

    private val _userId = MutableStateFlow<String?>(null)
    val userId: StateFlow<String?> get() = _userId

    fun login(id: String, password: String) {
        viewModelScope.launch {
            authRepository.login(id, password)
                .onSuccess { userId ->
                    _userId.value = userId.toString()
                    _loginState.value = UiState(true, "로그인 성공 ! userId는 $userId")
                }
                .onFailure {
                    if (it is HttpException) {
                        _loginState.value = UiState(false, it.message())
                    } else {
                        Log.e("LoginButton", "서버 닫혀서 ..", it)
                        _loginState.value = UiState(false, "로그인 실패")
                    }
                }
        }
    }
}