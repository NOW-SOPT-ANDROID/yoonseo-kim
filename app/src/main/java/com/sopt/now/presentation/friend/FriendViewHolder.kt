package com.sopt.now.presentation.friend

import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.databinding.ItemFriendBinding
import com.bumptech.glide.Glide
import com.sopt.now.domain.entity.FriendEntity

class FriendViewHolder(private val binding: ItemFriendBinding) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(friendData: FriendEntity) {
        binding.run {
            Glide.with(root.context)
                .load(friendData.avatar)
                .into(ivProfile)
            tvName.text = friendData.firstName
            tvPhone.text = friendData.email
        }
    }
}