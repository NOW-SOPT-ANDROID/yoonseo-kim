package com.sopt.now.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sopt.now.core.base.BindingFragment
import com.sopt.now.core.base.factory.BaseViewModelFactory
import com.sopt.now.data.ServicePool
import com.sopt.now.data.repoImpl.FriendRepositoryImpl
import com.sopt.now.data.repoImpl.UserRepositoryImpl
import com.sopt.now.data.repository.FriendRepository
import com.sopt.now.data.repository.UserRepository
import com.sopt.now.databinding.FragmentHomeBinding
import com.sopt.now.presentation.friend.FriendAdapter
import com.sopt.now.presentation.main.MainViewModel
import com.sopt.now.presentation.user.UserInfo

class HomeFragment : BindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val userRepository: UserRepository by lazy { UserRepositoryImpl(ServicePool.userService) }
    private val userViewModelFactory by lazy { BaseViewModelFactory(userRepository = userRepository) }
    private val mainViewModel: MainViewModel by viewModels { userViewModelFactory }

    private val friendRepository: FriendRepository by lazy { FriendRepositoryImpl(ServicePool.friendService) }
    private val friendViewModelFactory by lazy { BaseViewModelFactory(friendRepository = friendRepository) }
    private val homeViewModel: HomeViewModel by viewModels { friendViewModelFactory }

    private val friendAdapter by lazy { FriendAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        observeFriends()
        observeUserInfo()

        homeViewModel.getFriends()

        val userId = requireActivity().intent.getStringExtra("userId")
        userId?.let {
            mainViewModel.getUserInfo(it.toInt())
        }
    }

    private fun initAdapter() {
        binding.rvFriends.adapter = friendAdapter
    }

    private fun observeFriends() {
        homeViewModel.friends.observe(viewLifecycleOwner) { friends ->
            friendAdapter.setFriends(friends)
        }
    }

    private fun observeUserInfo() {
        mainViewModel.userInfo.observe(viewLifecycleOwner) { userInfo ->
            updateUI(userInfo)
        }
    }

    private fun updateUI(userInfo: UserInfo) {
        val friendAdapter = binding.rvFriends.adapter as? FriendAdapter
        friendAdapter?.setUser(userInfo)
    }
}