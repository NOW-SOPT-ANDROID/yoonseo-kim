package com.sopt.now.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.R
import com.sopt.now.ServicePool.authService
import com.sopt.now.presentation.main.MainActivity
import com.sopt.now.presentation.signup.SignUpActivity
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.dto.response.ResponseLoginDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        initObserver()
    }

    private fun initViews() {
        binding.btnLogin.setOnClickListener {
            viewModel.login(getLoginRequestDto())
        }
        binding.btnSignUp.setOnClickListener {
            navigateToSignUp()
        }
    }

    private fun initObserver() {
        viewModel.liveData.observe(this) {
            Toast.makeText(
                this@LoginActivity,
                it.message,
                Toast.LENGTH_SHORT,
            ).show()
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