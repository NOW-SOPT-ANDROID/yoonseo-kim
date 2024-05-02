package com.sopt.now.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.now.BindingFragment
import com.sopt.now.ServicePool.userService
import com.sopt.now.databinding.FragmentHomeBinding
import com.sopt.now.friend.FriendAdapter
import com.sopt.now.response.ResponseUserInfoDto
import com.sopt.now.user.UserInfo
import com.sopt.now.viewmodel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : BindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel = HomeViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val friendAdapter = FriendAdapter()
        binding.rvFriends.run {
            adapter = friendAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        val userId = requireActivity().intent.getStringExtra("userId")
        if (userId != null) {
            getUserInfo(userId.toInt())
        }
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
                        val userInfo = UserInfo(it.authenticationId, it.nickname, it.phone)
                        updateUI(userInfo)
                    }
                    Log.d("Home", "data: $data, userId: $userId")
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

    private fun updateUI(userInfo: UserInfo) {
        val friendAdapter = binding.rvFriends.adapter as FriendAdapter
        friendAdapter.setUser(userInfo)
    }
}