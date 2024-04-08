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

@Suppress("DEPRECATION") //getSerializableExtra deprecation 문제 처리
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    //상수화
    companion object {
        const val USER_INFO = "user_info"
    }

    //회원가입 결과를 처리하기 위한 ActivityResultLauncher
    private val signUpLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        //회원가입 액티비티 성공적 종료 시
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            //회원가입 액티비티에서 넘어온 사용자 정보 확인
            val userInfo = data?.getSerializableExtra(USER_INFO) as? UserInfo
            signUpResult(userInfo)
        }
        //회원가입 액티비티가 취소되었을 시
        else if (result.resultCode == RESULT_CANCELED) {
            showToast("회원가입이 취소되었습니다.")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            //입력된 정보
            val id = binding.etSignUpId.text.toString()
            val password = binding.etSignUpPwd.text.toString()
            val nickname = binding.etSignUpNickname.text.toString()
            val mbti = binding.etSignUpMbti.text.toString()

            if (isValidInfo(id, password, nickname)) {
                //회원가입 정보 생성
                val userInfo = UserInfo(id, password, nickname, mbti)
                loginSuccess(userInfo)
            } else {
                //유효하지 않은 입력일 경우 (조건 하나라도 틀리면 뜨는 두번째 Toast 메시지)
                showToast("회원가입에 실패하였습니다.")
            }
        }
    }

    //회원가입에 성공해서 LoginActivity로 사용자 정보를 전달하며 이동하는 함수
    private fun loginSuccess(userInfo: UserInfo) {
        val intent = Intent(this, LoginActivity::class.java).apply {
            putExtra(USER_INFO, userInfo)
        }
        signUpLauncher.launch(intent)
        showToast("회원가입에 성공하였습니다.")
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

    //입력된 정보가 유효한지 검사하는 함수
    private fun isValidInfo(id: String, password: String, nickname: String): Boolean {
        //각 조건 해당 Toast 메시지 표시 (하나라도 틀리면 false 반환)
        when {
            id.length !in 6..10 -> showToast("ID는 6~10글자여야 합니다.")
            password.length !in 8..12 -> showToast("비밀번호는 8~12글자여야 합니다.")
            nickname.isBlank() -> showToast("닉네임은 공백 없이 한 글자 이상이어야 합니다.")
            else -> return true
        }
        return false
    }
}