package com.sopt.now.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core.view.UiState
import com.sopt.now.data.ServicePool
import com.sopt.now.data.dto.request.RequestSignUpDto
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignUpViewModel : ViewModel() {

    private val authService by lazy { ServicePool.authService }
    private val _signUpState = MutableLiveData<UiState>()
    val signUpState: LiveData<UiState> get() = _signUpState

    fun signUp(request: RequestSignUpDto) {
        viewModelScope.launch {
            runCatching {
                authService.signUp(request)
            }.onSuccess {
                _signUpState.value = UiState(true, it.message)
            }.onFailure {
                if (it is HttpException) {
                    _signUpState.value = UiState(false, it.message())
                } else {
                    _signUpState.value = UiState(false, "로그인이 실패")
                }
            }
        }
    }
}