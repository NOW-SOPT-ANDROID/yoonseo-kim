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

        getUserInfo(userId)
        setUpListener()
    }

    private fun getUserInfo(userId: Int){
        val userId = arguments?.getInt("userId") ?: return
        userService.getUserInfo(userId).enqueue(object : Callback<ResponseUserInfoDto> {
            override fun onResponse(
                call: Call<ResponseUserInfoDto>,
                response: Response<ResponseUserInfoDto>,
            ) {
                if (response.isSuccessful) {
                    val data: ResponseUserInfoDto? = response.body()
                    updateUI(data?.data)
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

    private fun updateUI(userInfo: UserInfo?){
        userInfo?.let {
            binding.tvMyPageNickname = userInfo.nickname
            binding.tvMyPageId = userInfo.authenticationId
            binding.tvMyPagePhone = userInfo.phone
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