package com.sopt.now.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.sopt.now.BindingFragment
import com.sopt.now.activity.LoginActivity
import com.sopt.now.user.UserInfo
import com.sopt.now.databinding.FragmentMyPageBinding
import com.sopt.now.response.ResponseUserInfoDto
import com.sopt.now.ServicePool.userService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageFragment : BindingFragment<FragmentMyPageBinding>(FragmentMyPageBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = requireActivity().intent.getStringExtra("userId")
        if (userId != null) {
            getUserInfo(userId.toInt())
        }
        setUpListener()
    }

    private fun getUserInfo(userId: Int) {
        userService.getUserInfo(userId).enqueue(object : Callback<ResponseUserInfoDto> {
            override fun onResponse(
                call: Call<ResponseUserInfoDto>,
                response: Response<ResponseUserInfoDto>,
            ) {
                if (response.isSuccessful) {
                    val data: ResponseUserInfoDto? = response.body()
                    data?.data?.let {
                        updateUI(it)
                    }
                    Log.d("SignUp", "data: $data, userId: $userId")
                } else {
                    val error = response.errorBody()?.string() ?: response.message()
                    Log.d("Home", "error: $error")
                }
            }

            override fun onFailure(call: Call<ResponseUserInfoDto>, t: Throwable) {
                Log.d("Home", "onFailure", t)
            }
        })
    }

    private fun updateUI(data: ResponseUserInfoDto.Data) {
        with(binding) {
            tvMyPageNickname.text = data.nickname
            tvMyPageId.text = data.authenticationId
            tvMyPagePhone.text = data.phone
        }
    }

    private fun setUpListener() {
        binding.btnLogout.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}