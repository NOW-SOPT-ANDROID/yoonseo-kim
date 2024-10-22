package com.sopt.now.core.base.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sopt.now.data.repository.AuthRepository
import com.sopt.now.data.repository.FriendRepository
import com.sopt.now.data.repository.UserRepository
import com.sopt.now.presentation.home.HomeViewModel
import com.sopt.now.presentation.login.LoginViewModel
import com.sopt.now.presentation.main.MainViewModel
import com.sopt.now.presentation.signup.SignUpViewModel

class BaseViewModelFactory(
    private val authRepository: AuthRepository? = null,
    private val friendRepository: FriendRepository? = null,
    private val userRepository: UserRepository? = null
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return authRepository?.let { LoginViewModel(it) } as T
        }
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return authRepository?.let { SignUpViewModel(it) } as T
        }
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return friendRepository?.let { HomeViewModel(it) } as T
        }
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return userRepository?.let { MainViewModel(it) } as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}