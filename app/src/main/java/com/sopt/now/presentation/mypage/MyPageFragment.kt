package com.sopt.now.presentation.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.now.core.base.BindingFragment
import com.sopt.now.core.base.factory.BaseViewModelFactory
import com.sopt.now.presentation.login.LoginActivity
import com.sopt.now.databinding.FragmentMyPageBinding
import com.sopt.now.presentation.main.MainViewModel
import com.sopt.now.domain.entity.UserEntity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MyPageFragment : BindingFragment<FragmentMyPageBinding>(FragmentMyPageBinding::inflate) {

    private val viewModelFactory by lazy { BaseViewModelFactory() }
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
        viewModel.userInfo.flowWithLifecycle(lifecycle).onEach { userInfo ->
            updateUI(userInfo)
        }.launchIn(lifecycleScope)
    }

    private fun updateUI(userInfo: UserEntity) {
        with(binding) {
            tvMyPageNickname.text = userInfo.nickname
            tvMyPageId.text = userInfo.id
            tvMyPagePhone.text = userInfo.phone
        }
    }
}