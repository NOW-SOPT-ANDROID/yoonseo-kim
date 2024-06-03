package com.sopt.now

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.request.RequestSignUpDto
import com.sopt.now.response.ResponseSignUpDto
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class SignUpViewModel : ViewModel() {

    private val authService by lazy { ServicePool.authService }
    private val _signUpState = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState> = _signUpState

    // 코루틴 사용 - 2번째 방법
    fun signUp(request: RequestSignUpDto) {
        viewModelScope.launch {
            // DTO 자체를 리턴 타입으로 하면, http exception을 잡아야 하니까 try, catch문 사용
            // (successful한지를 retrofit에서 내부적으로 exception으로 제공해줌)
//            try {
//                val dto = authService.signUp(request)
//                liveData.value = SignUpState(true, dto.message)
//            } catch (e: HttpException) {
//                liveData.value = SignUpState(false, "회원가입 실패 ${e.code()}")
//            }

            runCatching {
                authService.signUp(request)
            }.onSuccess {
                _signUpState.value = SignUpState(true, "회원가입 성공")
            }.onFailure {
                if (it is HttpException) {
                    _signUpState.value = SignUpState(false, it.message())
                } else {
                    _signUpState.value = SignUpState(false, "회원가입 실패")
                }
            }
        }
    }
}