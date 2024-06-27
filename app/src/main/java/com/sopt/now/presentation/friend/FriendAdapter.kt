package com.sopt.now.presentation.friend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.databinding.ItemFriendBinding
import com.sopt.now.databinding.ItemUserBinding
import com.sopt.now.domain.entity.FriendEntity
import com.sopt.now.presentation.user.UserViewHolder

class FriendAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var friends: List<FriendEntity> = emptyList()
    private var user = UserEntity("", "", "")

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_USER
            else -> TYPE_FRIEND
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_USER -> {
                val binding = ItemUserBinding.inflate(inflater, parent, false)
                UserViewHolder(binding)
            }
            TYPE_FRIEND -> {
                val binding = ItemFriendBinding.inflate(inflater, parent, false)
                FriendViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserViewHolder -> holder.onBind(user)
            is FriendViewHolder -> holder.onBind(friends[position - 1])
        }
    }

    override fun getItemCount() : Int {
        return friends.size + 1
    }

    fun setFriends(friends: List<FriendEntity>) {
        this.friends = friends.toList()
        notifyDataSetChanged()
    }

    fun setUser(userInfo: UserEntity) {
        user = userInfo
        notifyDataSetChanged()
    }

    companion object {
        private const val TYPE_USER = 0
        private const val TYPE_FRIEND = 1
    }
}