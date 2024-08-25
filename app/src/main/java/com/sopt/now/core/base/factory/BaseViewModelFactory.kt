package com.sopt.now.core.base.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sopt.now.data.ServicePool
import com.sopt.now.data.datasourceImpl.AuthDataSourceImpl
import com.sopt.now.data.datasourceImpl.FriendDataSourceImpl
import com.sopt.now.data.datasourceImpl.UserDataSourceImpl
import com.sopt.now.data.repoImpl.AuthRepositoryImpl
import com.sopt.now.data.repoImpl.FriendRepositoryImpl
import com.sopt.now.data.repoImpl.UserRepositoryImpl
import com.sopt.now.presentation.home.HomeViewModel
import com.sopt.now.presentation.login.LoginViewModel
import com.sopt.now.presentation.main.MainViewModel
import com.sopt.now.presentation.signup.SignUpViewModel

class BaseViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                val repository = AuthRepositoryImpl(AuthDataSourceImpl(ServicePool.authService))
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                val repository = AuthRepositoryImpl(AuthDataSourceImpl(ServicePool.authService))
                SignUpViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                val repository = FriendRepositoryImpl(FriendDataSourceImpl(ServicePool.friendService))
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                val repository = UserRepositoryImpl(UserDataSourceImpl(ServicePool.userService))
                MainViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}