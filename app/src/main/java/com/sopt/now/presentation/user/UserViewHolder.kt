package com.sopt.now.presentation.user

import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.databinding.ItemUserBinding
import com.sopt.now.domain.entity.UserEntity

class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){

    fun onBind(user: UserEntity) {
        binding.run {
            tvName.text = user.nickname
            tvPhone.text = user.phone
        }
    }
}