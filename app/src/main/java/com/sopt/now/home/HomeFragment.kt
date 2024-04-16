package com.sopt.now.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.now.R
import com.sopt.now.user.UserInfo
import com.sopt.now.databinding.FragmentHomeBinding
import com.sopt.now.friend.Friend
import com.sopt.now.friend.FriendAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding)

    private val viewModel = HomeViewModel()
    private var userInfo: UserInfo? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        userInfo = arguments?.getParcelable(USER_INFO)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val friendAdapter = FriendAdapter()
        binding.rvFriends.run {
            adapter = friendAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        friendAdapter.setFriendList(viewModel.mockFriendList)
        userInfo?.let { friendAdapter.setUser(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val USER_INFO = "user_info"
    }
}