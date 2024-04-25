package com.sopt.now

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userInfo = intent?.getParcelableExtra<UserInfo>(USER_INFO)

        userInfo?.let {
            with(binding) {
                tvMyPageNickname.text = it.nickname
                tvMyPageMbti.text = it.mbti
                tvMyPageId.text = it.id
                tvMyPagePwd.text = it.password
            }
        }
    }

    companion object {
        const val USER_INFO = "user_info"
    }
}