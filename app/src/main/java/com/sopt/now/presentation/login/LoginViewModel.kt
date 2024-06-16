package com.sopt.now.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core.view.UiState
import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginState = MutableLiveData<UiState>()
    val loginState: LiveData<UiState> get() = _loginState

    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> get() = _userId

    fun login(request: RequestLoginDto) {
        viewModelScope.launch {
            authRepository.login(request)
                .onSuccess { userId ->
                    _userId.value = userId
                    _loginState.value = UiState(true, "로그인 성공")
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