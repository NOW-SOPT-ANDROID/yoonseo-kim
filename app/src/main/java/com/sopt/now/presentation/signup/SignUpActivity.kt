package com.sopt.now.presentation.signup

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.core.base.AuthViewModelFactory
import com.sopt.now.core.util.showToast
import com.sopt.now.data.ServicePool
import com.sopt.now.databinding.ActivitySignUpBinding
import com.sopt.now.data.dto.request.RequestSignUpDto
import com.sopt.now.data.repository.AuthRepository

class SignUpActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }

    private val authRepository by lazy { AuthRepository(ServicePool.authService) }
    private val viewModelFactory by lazy { AuthViewModelFactory(authRepository) }

    private val viewModel: SignUpViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        initObserver()
    }

    private fun initViews() {
        binding.btnSignUp.setOnClickListener {
            viewModel.signUp(getSignUpRequestDto())
        }
    }

    private fun initObserver() {
        viewModel.signUpState.observe(this) {
            this@SignUpActivity.showToast(it.message)
            if (it.isSuccess) {
                navigateToLogin()
            }
        }
    }

    private fun navigateToLogin() {
        finish()
    }

    private fun getSignUpRequestDto(): RequestSignUpDto {
        val id = binding.etSignUpId.text.toString()
        val password = binding.etSignUpPwd.text.toString()
        val nickname = binding.etSignUpNickname.text.toString()
        val phoneNumber = binding.etSignUpPhone.text.toString()
        return RequestSignUpDto(
            authenticationId = id,
            password = password,
            nickname = nickname,
            phone = phoneNumber
        )
    }
}