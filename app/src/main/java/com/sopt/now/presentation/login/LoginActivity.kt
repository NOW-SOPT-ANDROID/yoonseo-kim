package com.sopt.now.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.now.core.base.factory.BaseViewModelFactory
import com.sopt.now.core.util.showToast
import com.sopt.now.data.ServicePool
import com.sopt.now.presentation.main.MainActivity
import com.sopt.now.presentation.signup.SignUpActivity
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.repoImpl.AuthRepositoryImpl
import com.sopt.now.domain.repository.AuthRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    private val authRepository: AuthRepository by lazy { AuthRepositoryImpl(ServicePool.authService) }
    private val viewModelFactory by lazy { BaseViewModelFactory(authRepository = authRepository) }

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
        viewModel.loginState.flowWithLifecycle(lifecycle).onEach { uiState ->
            if (uiState.isSuccess) {
                this@LoginActivity.showToast(uiState.message)
                navigateToMain()
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToMain() {
        viewModel.userId.flowWithLifecycle(lifecycle).onEach { userId ->
            userId?.let {
                val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
                    putExtra("userId", it)
                }
                startActivity(intent)
                finish()
            }
        }.launchIn(lifecycleScope)
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