package com.sopt.now.user

import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.databinding.ItemUserBinding

class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){

    fun onBind(user: UserInfo) {
        binding.run {
            tvName.text = user.nickname
            tvMbti.text = user.mbti
        }
    }
}