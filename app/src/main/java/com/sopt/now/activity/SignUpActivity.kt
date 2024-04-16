package com.sopt.now.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.R
import com.sopt.now.user.UserInfo
import com.sopt.now.databinding.ActivitySignUpBinding
import com.sopt.now.showToast

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    companion object {
        const val USER_INFO = "user_info"
    }

    private val signUpLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val userInfo = data?.getParcelableExtra<UserInfo>(USER_INFO)
            signUpResult(userInfo)
        }
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
                    val userInfo = UserInfo(id, password, nickname, mbti)
                    loginSuccess(userInfo)
                } else {
                    showToast(getString(R.string.sign_up_fail_message))
                }
            }
        }
    }

    private fun loginSuccess(userInfo: UserInfo) {
        val intent = Intent(this, LoginActivity::class.java).apply {
            putExtra(USER_INFO, userInfo)
        }
        signUpLauncher.launch(intent)
        showToast(getString(R.string.sign_up_success_message))
    }

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