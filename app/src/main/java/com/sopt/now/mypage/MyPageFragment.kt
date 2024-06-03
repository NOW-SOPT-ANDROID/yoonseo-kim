package com.sopt.now.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.sopt.now.BindingFragment
import com.sopt.now.activity.LoginActivity
import com.sopt.now.databinding.FragmentMyPageBinding
import com.sopt.now.response.ResponseUserInfoDto
import com.sopt.now.ServicePool.userService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageFragment : BindingFragment<FragmentMyPageBinding>(FragmentMyPageBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().intent.getStringExtra("userId")?.toInt()?.let {
            getUserInfo(it)
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
                    Log.d("MyPage", "data: $data, userId: $userId")
                } else {
                    val error = response.errorBody()?.string() ?: response.message()
                    Log.d("MyPage", "error: $error")
                }
            }

            override fun onFailure(call: Call<ResponseUserInfoDto>, t: Throwable) {
                Log.d("MyPage", "onFailure", t)
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
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            activity?.finish()
        }
    }
}