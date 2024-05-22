package com.sopt.now.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.now.util.base.BindingFragment
import com.sopt.now.data.ServicePool.friendService
import com.sopt.now.data.ServicePool.userService
import com.sopt.now.databinding.FragmentHomeBinding
import com.sopt.now.presentation.friend.Friend
import com.sopt.now.presentation.friend.FriendAdapter
import com.sopt.now.data.dto.response.ResponseFriendDto
import com.sopt.now.data.dto.response.ResponseUserInfoDto
import com.sopt.now.presentation.user.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : BindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        getFriends()

        val userId = requireActivity().intent.getStringExtra("userId")
        userId?.let {
            getUserInfo(it.toInt())
        }
    }

    private fun initAdapter() {
        val friendAdapter = FriendAdapter()
        binding.rvFriends.run {
            adapter = friendAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun getFriends() {
        friendService.getFriends(2).enqueue(object : Callback<ResponseFriendDto> {
            override fun onResponse(
                call: Call<ResponseFriendDto>,
                response: Response<ResponseFriendDto>
            ) {
                if (response.isSuccessful) {
                    val data: ResponseFriendDto? = response.body()
                    val friends = data?.data?.map {
                        Friend(it.id, it.email, it.firstName, it.lastName, it.avatar)
                    } ?: listOf()
                    val friendAdapter = binding.rvFriends.adapter as FriendAdapter
                    friendAdapter.setFriends(friends)
                    Log.d("FriendListSuc", "data: $data")
                } else {
                    val error = response.errorBody()?.string() ?: response.message()
                    Log.d("FriendList", "error: $error")
                }
            }

            override fun onFailure(call: Call<ResponseFriendDto>, t: Throwable) {
                Log.d("FriendList", "onFailure", t)
            }
        })
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
        val friendAdapter = binding.rvFriends.adapter as? FriendAdapter
        friendAdapter?.setUser(userInfo)
    }
}