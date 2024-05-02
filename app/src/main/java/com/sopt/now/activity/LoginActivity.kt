package com.sopt.now.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.R
import com.sopt.now.ServicePool.authService
import com.sopt.now.user.UserInfo
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.home.HomeFragment
import com.sopt.now.request.RequestLoginDto
import com.sopt.now.request.RequestSignUpDto
import com.sopt.now.response.ResponseLoginDto
import com.sopt.now.response.ResponseSignUpDto
import com.sopt.now.showToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        binding.btnLogin.setOnClickListener {
            login()
        }
        binding.btnSignUp.setOnClickListener {
            switchToSignUp()
        }
    }

    private fun login() {
        val loginRequest = getLoginRequestDto()
        authService.login(loginRequest).enqueue(object : Callback<ResponseLoginDto> {
            override fun onResponse(
                call: Call<ResponseLoginDto>,
                response: Response<ResponseLoginDto>,
            ) {
                if (response.isSuccessful) {
                    val data: ResponseLoginDto? = response.body()
                    val userId = response.headers()["location"]
                    Toast.makeText(
                        this@LoginActivity,
                        "회원가입 성공 유저의 ID는 $userId 입니둥",
                        Toast.LENGTH_SHORT,
                    ).show()
                    Log.d("Login", "data: $data, userId: $userId")
                    switchToMain(userId)
                } else {
                    val error = response.message()
                    Toast.makeText(
                        this@LoginActivity,
                        "로그인이 실패 $error",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "서버 에러 발생 ", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun switchToMain(userId: String?) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("userId", userId)
        }
        startActivity(intent)
        finish()
    }

    private fun switchToSignUp(){
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