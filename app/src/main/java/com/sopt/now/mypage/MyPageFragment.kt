package com.sopt.now.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.sopt.now.BindingFragment
import com.sopt.now.activity.LoginActivity
import com.sopt.now.user.UserInfo
import com.sopt.now.databinding.FragmentMyPageBinding

class MyPageFragment : BindingFragment<FragmentMyPageBinding>(FragmentMyPageBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userInfo = arguments?.getParcelable<UserInfo>(USER_INFO)
        userInfo?.let {
            with(binding) {
                tvMyPageNickname.text = it.nickname
                tvMyPageMbti.text = it.mbti
                tvMyPageId.text = it.id
                tvMyPagePwd.text = it.password
            }
        }
        setUpListener()
    }

    private fun setUpListener() {
        binding.btnLogout.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    companion object {
        const val USER_INFO = "user_info"
    }
}