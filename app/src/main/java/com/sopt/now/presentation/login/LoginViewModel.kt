package com.sopt.now.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core.view.UiState
import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginState = MutableStateFlow(UiState())
    val loginState: StateFlow<UiState> get() = _loginState

    private val _userId = MutableStateFlow<String?>(null)
    val userId: StateFlow<String?> get() = _userId

    fun login(request: RequestLoginDto) {
        viewModelScope.launch {
            authRepository.login(request)
                .onSuccess { userId ->
                    _userId.value = userId
                    _loginState.value = UiState(true, "로그인 성공 ! userId는 $userId")
                }
                .onFailure {
                    if (it is HttpException) {
                        _loginState.value = UiState(false, it.message())
                    } else {
                        _loginState.value = UiState(false, "로그인 실패")
                    }
                }
        }
    }
}