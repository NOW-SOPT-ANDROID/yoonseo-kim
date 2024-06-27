package com.sopt.now.presentation.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core.view.UiState
import com.sopt.now.data.dto.request.RequestSignUpDto
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignUpViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _signUpState = MutableStateFlow(UiState())
    val signUpState: StateFlow<UiState> get() = _signUpState

    fun signUp(userEntity: UserEntity) {
        viewModelScope.launch {
            authRepository.signUp(userEntity)
                .onSuccess {
                    _signUpState.value = UiState(true, "회원가입 성공")
                    Log.e("SignUpButton", "회원가입 성공됨")
                }
                .onFailure {
                    if (it is HttpException) {
                        _signUpState.value = UiState(false, it.message())
                        Log.e("SignUpButton", "회원가입 http exception 오류")
                    } else {
                        _signUpState.value = UiState(false, "회원가입 실패")
                        Log.e("SignUpButton", "뭔가 회원가입 조건 틀렸나봐")
                    }
                }
        }
    }
}