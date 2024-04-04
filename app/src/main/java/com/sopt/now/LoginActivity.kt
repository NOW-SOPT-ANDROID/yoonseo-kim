package com.sopt.now

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.UserInfo
import com.sopt.now.databinding.ActivityLoginBinding

@Suppress("DEPRECATION") //getSerializableExtra deprecation 문제 처리
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val signUpLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val userInfo = data?.getSerializableExtra("user_info") as? UserInfo
            userInfo?.let { switchToMain(it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //로그인 하기 버튼 눌렀을 때 (회원가입 정보와 비교해서 확인)
        binding.loginButton.setOnClickListener {
            val id = binding.idTextfield.text.toString()
            val password = binding.passwordTextfield.text.toString()
            val userInfo = intent?.getSerializableExtra("user_info") as? UserInfo
            if (userInfo != null && userInfo.id == id && userInfo.password == password) {
                showToast("로그인에 성공하였습니다.")
                switchToMain(userInfo)
            } else {  //입력 정보 하나라도 틀리면
                showToast("아이디 또는 비밀번호가 틀립니다.")
            }
        }

        //회원가입 하기 버튼 눌렀을 때 (SignUpActivity로 화면 전환)
        binding.signupButton.setOnClickListener {
            signUpLauncher.launch(Intent(this, SignUpActivity::class.java))
        }
    }

    //로그인 성공 시 MainActivity로 전환되는 함수
    private fun switchToMain(userInfo: UserInfo) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("user_info", userInfo)
        }
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}