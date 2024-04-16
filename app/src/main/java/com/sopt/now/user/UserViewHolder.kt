package com.sopt.now.user

import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.databinding.ItemUserBinding

class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){

    fun onBind(userData: UserInfo) {
        binding.run {
            tvName.text = userData.nickname
            tvMbti.text = userData.mbti
        }
    }
}