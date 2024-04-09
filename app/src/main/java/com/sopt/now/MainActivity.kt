package com.sopt.now

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sopt.now.UserInfo
import com.sopt.now.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        const val USER_INFO = "user_info"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // LoginActivity로부터 전달된 사용자 정보를 받음
        val userInfo = intent?.getParcelableExtra<UserInfo>(USER_INFO)

        // 사용자 정보가 null이 아니라면 TextView에 정보 표시
        userInfo?.let {
            binding.tvMyPageNickname.text = it.nickname
            binding.tvMyPageMbti.text = it.mbti
            binding.tvMyPageId.text = it.id
            binding.tvMyPagePwd.text = it.password
        }
    }
}