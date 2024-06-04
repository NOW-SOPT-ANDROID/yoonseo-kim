package com.sopt.now.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.now.core.base.BindingFragment
import com.sopt.now.databinding.FragmentHomeBinding
import com.sopt.now.presentation.friend.FriendAdapter
import com.sopt.now.presentation.main.MainViewModel
import com.sopt.now.presentation.user.UserInfo

class HomeFragment : BindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val mainViewModel by viewModels<MainViewModel>()
    private val homeViewModel by viewModels<HomeViewModel>()

    private val friendAdapter = FriendAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        homeViewModel.friends.observe(viewLifecycleOwner) { friends ->
            friendAdapter.setFriends(friends)
        }

        mainViewModel.userInfo.observe(viewLifecycleOwner) { userInfo ->
            updateUI(userInfo)
        }

        homeViewModel.getFriends()

        val userId = requireActivity().intent.getStringExtra("userId")
        userId?.let {
            mainViewModel.getUserInfo(it.toInt())
        }
    }

    private fun initAdapter() {
        binding.rvFriends.run {
            adapter = friendAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun updateUI(userInfo: UserInfo) {
        val friendAdapter = binding.rvFriends.adapter as? FriendAdapter
        friendAdapter?.setUser(userInfo)
    }
}