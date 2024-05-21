package com.sopt.now.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.now.BindingFragment
import com.sopt.now.user.UserInfo
import com.sopt.now.databinding.FragmentHomeBinding
import com.sopt.now.friend.FriendAdapter

class HomeFragment : BindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel = HomeViewModel()
    private var userInfo: UserInfo? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userInfo = arguments?.getParcelable(USER_INFO)

        val friendAdapter = FriendAdapter()
        binding.rvFriends.run {
            adapter = friendAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        friendAdapter.setFriendList(viewModel.mockFriendList)
        userInfo?.let { friendAdapter.setUser(it) }
    }

    companion object {
        const val USER_INFO = "user_info"
    }
}