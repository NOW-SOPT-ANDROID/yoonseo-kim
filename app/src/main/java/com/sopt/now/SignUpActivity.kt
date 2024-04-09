package com.sopt.now

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.UserInfo
import com.sopt.now.databinding.ActivitySignUpBinding
import com.google.android.material.snackbar.Snackbar

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    companion object {
        const val USER_INFO = "user_info"
    }

    //회원가입 결과를 처리하기 위한 ActivityResultLauncher
    private val signUpLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        //회원가입 액티비티 성공적 종료 시
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            //회원가입 액티비티에서 넘어온 사용자 정보 확인
            val userInfo = data?.getParcelableExtra<UserInfo>(USER_INFO)
            signUpResult(userInfo)
        }
        //회원가입 액티비티가 취소되었을 시
        else if (result.resultCode == RESULT_CANCELED) {
            showToast(getString(R.string.sign_up_canceled_message))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            with(binding) {
                val id = etSignUpId.text.toString()
                val password = etSignUpPwd.text.toString()
                val nickname = etSignUpNickname.text.toString()
                val mbti = etSignUpMbti.text.toString()

                if (isSignUpAvailable(id, password, nickname)) {
                    //회원가입 정보 생성
                    val userInfo = UserInfo(id, password, nickname, mbti)
                    loginSuccess(userInfo)
                } else {
                    //유효하지 않은 입력일 경우 (조건 하나라도 틀리면 뜨는 두번째 Toast 메시지)
                    showToast(getString(R.string.sign_up_fail_message))
                }
            }
        }
    }

    //회원가입에 성공해서 LoginActivity로 사용자 정보를 전달하며 이동하는 함수
    private fun loginSuccess(userInfo: UserInfo) {
        val intent = Intent(this, LoginActivity::class.java).apply {
            putExtra(USER_INFO, userInfo)
        }
        signUpLauncher.launch(intent)
        showToast(getString(R.string.sign_up_success_message))
    }

    //회원가입 결과 처리 함수
    private fun signUpResult(userInfo: UserInfo?) {
        if (userInfo != null) {
            val resultIntent = Intent().apply {
                putExtra(USER_INFO, userInfo)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun isSignUpAvailable(id: String, password: String, nickname: String): Boolean {
        when {
            id.length !in 6..10 -> showToast(getString(R.string.id_wrong_message))
            password.length !in 8..12 -> showToast(getString(R.string.pwd_wrong_message))
            nickname.isBlank() || nickname.contains(" ") -> showToast(getString(R.string.nickname_wrong_message))
            else -> return true
        }
        return false
    }
}