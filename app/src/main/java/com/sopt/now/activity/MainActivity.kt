package com.sopt.now.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sopt.now.R
import com.sopt.now.databinding.ActivityMainBinding
import com.sopt.now.home.HomeFragment
import com.sopt.now.mypage.MyPageFragment
import com.sopt.now.search.SearchFragment
import com.sopt.now.user.UserInfo


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var userInfo : UserInfo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userInfo = intent.getParcelableExtra(LoginActivity.USER_INFO)

        initHomeFragment()
        clickBottomNavigation()
    }

    private fun initHomeFragment(){
        val currentFragment = supportFragmentManager.findFragmentById(binding.fcvHome.id)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(binding.fcvHome.id, HomeFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(LoginActivity.USER_INFO, userInfo)
                    }
                })
                .commit()
        }
    }

    private fun clickBottomNavigation() {
        binding.bnvHome.setOnItemSelectedListener{
            when (it.itemId) {
                R.id.menu_home-> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.menu_search-> {
                    replaceFragment(SearchFragment())
                    true
                }

                R.id.menu_mypage-> {
                    replaceFragment(MyPageFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        fragment.arguments = Bundle().apply {
            putParcelable(LoginActivity.USER_INFO, userInfo)
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_home, fragment)
            .commit()
    }

}