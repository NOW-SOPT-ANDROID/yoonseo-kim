package com.sopt.now.compose.presentation.signup

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.data.ServicePool
import com.sopt.now.compose.presentation.login.LoginActivity
import com.sopt.now.compose.core.view.UiState
import com.sopt.now.compose.data.dto.request.RequestSignUpDto
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignUpViewModel : ViewModel() {

    private val authService by lazy { ServicePool.authService }
    private val _signUpState = MutableLiveData<UiState>()
    val signUpState: LiveData<UiState> get() = _signUpState

    fun signUp(request: RequestSignUpDto, activity: Activity) {
        viewModelScope.launch {
            runCatching {
                authService.signUp(request)
            }.onSuccess {
                val userId = it.headers()["location"]
                _signUpState.value = UiState(true, "회원가입 성공 !")
                navigateToLogin(userId, activity)
            }.onFailure {
                if (it is HttpException) {
                    _signUpState.value = UiState(false, it.message())
                } else {
                    _signUpState.value = UiState(false, "로그인 실패")
                }
            }
        }
    }

    private fun navigateToLogin(userId: String?, activity: Activity) {
        val intent = Intent(activity, LoginActivity::class.java).apply {
            putExtra("userId", userId)
        }
        activity.startActivity(intent)
        activity.finish()
    }
}