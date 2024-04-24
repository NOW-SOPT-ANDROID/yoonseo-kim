package com.sopt.now.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.R
import com.sopt.now.user.UserInfo
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.showToast

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val signUpLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val userInfo = data?.getParcelableExtra<UserInfo>(USER_INFO)
            userInfo?.let { switchToMain(it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpListener()
    }

    private fun setUpListener() {
        binding.btnLogin.setOnClickListener {
            val id = binding.etLoginId.text.toString()
            val password = binding.etLoginPwd.text.toString()
            val userInfo = intent?.getParcelableExtra<UserInfo>(USER_INFO)

            userInfo?.let {
                if (it.id == id && it.password == password) {
                    showToast(getString(R.string.login_success_message))
                    switchToMain(it)
                } else {
                    showToast(getString(R.string.login_fail_message))
                }
            } ?: showToast(getString(R.string.login_fail_message))
        }

        binding.btnSignUp.setOnClickListener {
            signUpLauncher.launch(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun switchToMain(userInfo: UserInfo) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(USER_INFO, userInfo)
        }
        startActivity(intent)
        finish()
    }

    companion object {
        const val USER_INFO = "user_info"
    }
}