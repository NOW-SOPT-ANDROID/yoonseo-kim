package com.sopt.now.presentation.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.sopt.now.core.base.BindingFragment
import com.sopt.now.core.base.factory.UserViewModelFactory
import com.sopt.now.data.ServicePool
import com.sopt.now.data.repository.UserRepository
import com.sopt.now.presentation.login.LoginActivity
import com.sopt.now.databinding.FragmentMyPageBinding
import com.sopt.now.presentation.main.MainViewModel
import com.sopt.now.presentation.user.UserInfo


class MyPageFragment : BindingFragment<FragmentMyPageBinding>(FragmentMyPageBinding::inflate) {

    private val userRepository by lazy { UserRepository(ServicePool.userService) }
    private val viewModelFactory by lazy { UserViewModelFactory(userRepository) }
    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().intent.getStringExtra("userId")?.toInt()?.let {
            viewModel.getUserInfo(it)
        }

        setUpListener()

        observeUserInfo()
    }

    private fun setUpListener() {
        binding.btnLogout.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun observeUserInfo() {
        viewModel.userInfo.observe(viewLifecycleOwner) { userInfo ->
            updateUI(userInfo)
        }
    }

    private fun updateUI(userInfo: UserInfo) {
        with(binding) {
            tvMyPageNickname.text = userInfo.nickname
            tvMyPageId.text = userInfo.authenticationId
            tvMyPagePhone.text = userInfo.phone
        }
    }
}