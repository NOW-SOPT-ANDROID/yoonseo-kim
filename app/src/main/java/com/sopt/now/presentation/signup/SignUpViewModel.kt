package com.sopt.now.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.ServicePool
import com.sopt.now.data.dto.request.RequestSignUpDto
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignUpViewModel : ViewModel() {

    private val authService by lazy { ServicePool.authService }
    private val _liveData = MutableLiveData<SignUpState>()
    val liveData: LiveData<SignUpState> get() = _liveData

    fun signUp(request: RequestSignUpDto) {
        viewModelScope.launch {
            runCatching {
                authService.signUp(request)
            }.onSuccess {
                _liveData.value = SignUpState(true, it.message)
            }.onFailure {
                if (it is HttpException) {
                    _liveData.value = SignUpState(false, it.message())
                } else {
                    _liveData.value = SignUpState(false, "로그인이 실패")
                }
            }
        }
    }
}