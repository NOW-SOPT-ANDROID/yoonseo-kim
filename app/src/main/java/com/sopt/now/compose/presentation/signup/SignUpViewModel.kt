package com.sopt.now.compose.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.core.view.UiState
import com.sopt.now.compose.data.dto.request.RequestSignUpDto
import com.sopt.now.compose.data.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignUpViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _signUpState = MutableLiveData<UiState>()
    val signUpState: LiveData<UiState> get() = _signUpState

    fun signUp(request: RequestSignUpDto) {
        viewModelScope.launch {
            authRepository.signUp(request)
                .onSuccess {
                    _signUpState.value = UiState(true, "회원가입 성공 !")
                }
                .onFailure {
                    if (it is HttpException) {
                        _signUpState.value = UiState(false, it.message())
                    } else {
                        _signUpState.value = UiState(false, "회원가입 실패")
                    }
                }
        }
    }
}