package com.sopt.now.presentation.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.presentation.login.LoginActivity
import com.sopt.now.databinding.ActivitySignUpBinding
import com.sopt.now.data.dto.request.RequestSignUpDto

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
        }
    }

    private fun initObserver() {
        viewModel.liveData.observe(this) {
            Toast.makeText(
                this@SignUpActivity,
                it.message,
                Toast.LENGTH_SHORT,
            ).show()
            if (it.isSuccess) {
                navigateToLogin()
            }
        }
    }

//    private fun signUp() {
//        val signUpRequest = getSignUpRequestDto()
//        authService.signUp(signUpRequest).enqueue(object : Callback<ResponseSignUpDto> {
//            override fun onResponse(
//                call: Call<ResponseSignUpDto>,
//                response: Response<ResponseSignUpDto>,
//            ) {
//                if (response.isSuccessful) {
//                    val data: ResponseSignUpDto? = response.body()
//                    val userId = response.headers()["location"]
//                    Toast.makeText(
//                        this@SignUpActivity,
//                        "회원가입 성공 유저의 ID는 $userId 입니둥",
//                        Toast.LENGTH_SHORT,
//                    ).show()
//                    Log.d("SignUp", "data: $data, userId: $userId")
//                    navigateToLogin(userId)
//                } else {
//                    val error = response.message()
//                    Toast.makeText(
//                        this@SignUpActivity,
//                        "로그인이 실패 $error",
//                        Toast.LENGTH_SHORT,
//                    ).show()
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
//                Toast.makeText(this@SignUpActivity, "서버 에러 발생 ", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }

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