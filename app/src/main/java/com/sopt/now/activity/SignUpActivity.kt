package com.sopt.now.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.sopt.now.ServicePool
import com.sopt.now.SignUpViewModel
import com.sopt.now.databinding.ActivitySignUpBinding
import com.sopt.now.request.RequestSignUpDto
import com.sopt.now.response.ResponseSignUpDto

class SignUpActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        initObserver()
    }

    private fun initViews() {
        binding.btnSignUp.setOnClickListener {
            viewModel.signUp(getSignUpRequestDto())
            navigateToLogin()
        }
    }

    private fun initObserver() {
        viewModel.signUpState.observe(this) {
            Toast.makeText(
                this@SignUpActivity,
                it.message,
                Toast.LENGTH_SHORT,
            ).show()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
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