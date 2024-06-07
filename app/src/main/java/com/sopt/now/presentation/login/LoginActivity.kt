package com.sopt.now.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.core.base.factory.AuthViewModelFactory
import com.sopt.now.core.util.showToast
import com.sopt.now.data.ServicePool
import com.sopt.now.presentation.main.MainActivity
import com.sopt.now.presentation.signup.SignUpActivity
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.repository.AuthRepository

class LoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    private val authRepository by lazy { AuthRepository(ServicePool.authService) }
    private val viewModelFactory by lazy { AuthViewModelFactory(authRepository) }

    private val viewModel: LoginViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        initObserver()
    }

    private fun initViews() {
        binding.btnLogin.setOnClickListener {
            viewModel.login(getLoginRequestDto())
            navigateToMain()
        }
        binding.btnSignUp.setOnClickListener {
            navigateToSignUp()
        }
    }

    private fun initObserver() {
        viewModel.loginState.observe(this) {
            if (it.isSuccess) {
                this@LoginActivity.showToast(it.message)
            }
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        viewModel.userId.observe(this) {
            intent.putExtra("userId", viewModel.userId.value)
            startActivity(intent)
            finish()
        }
    }

    private fun navigateToSignUp(){
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun getLoginRequestDto(): RequestLoginDto {
        val id = binding.etLoginId.text.toString()
        val password = binding.etLoginPwd.text.toString()
        return RequestLoginDto(
            authenticationId = id,
            password = password
        )
    }
}