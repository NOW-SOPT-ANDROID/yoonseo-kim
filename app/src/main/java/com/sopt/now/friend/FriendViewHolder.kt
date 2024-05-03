package com.sopt.now.friend

import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.databinding.ItemFriendBinding
import com.bumptech.glide.Glide

class FriendViewHolder(private val binding: ItemFriendBinding) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(friendData: Friend) {
        binding.run {
            Glide.with(root.context)
                .load(friendData.avatar)
                .into(ivProfile)
            tvName.text = friendData.firstName
            tvPhone.text = friendData.email
        }
    }
}