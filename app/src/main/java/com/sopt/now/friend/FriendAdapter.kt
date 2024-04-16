package com.sopt.now.friend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.user.UserInfo
import com.sopt.now.databinding.ItemFriendBinding
import com.sopt.now.databinding.ItemUserBinding
import com.sopt.now.user.UserViewHolder

class FriendAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var friendList: List<Friend> = emptyList()
    private var user = UserInfo("", "", "", "")

    companion object {
        private const val TYPE_USER = 0
        private const val TYPE_FRIEND = 1
    }

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
            is FriendViewHolder -> holder.onBind(friendList[position - 1])
        }
    }

    override fun getItemCount() : Int {
        return friendList.size + 1
    }

    fun setFriendList(friendList: List<Friend>) {
        this.friendList = friendList.toList()
        notifyDataSetChanged()
    }

    fun setUser(userInfo: UserInfo) {
        this.user = userInfo
        notifyDataSetChanged()
    }
}