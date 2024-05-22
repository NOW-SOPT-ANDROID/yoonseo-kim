package com.sopt.now.compose.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sopt.now.compose.BottomNavigationItem
import com.sopt.now.compose.activity.MainActivity.Companion.HOME
import com.sopt.now.compose.activity.MainActivity.Companion.HOME_SCREEN
import com.sopt.now.compose.activity.MainActivity.Companion.MY_PAGE
import com.sopt.now.compose.activity.MainActivity.Companion.MY_PAGE_SCREEN
import com.sopt.now.compose.activity.MainActivity.Companion.SEARCH
import com.sopt.now.compose.activity.MainActivity.Companion.SEARCH_SCREEN
import com.sopt.now.compose.home.HomeScreen
import com.sopt.now.compose.mypage.MyPageScreen
import com.sopt.now.compose.search.SearchScreen
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import com.sopt.now.compose.user.UserInfo

class MainActivity : ComponentActivity() {

    companion object {
        const val USER_INFO = "user_info"
        const val HOME = "Home"
        const val SEARCH = "Search"
        const val MY_PAGE = "My Page"
        const val HOME_SCREEN = 0
        const val SEARCH_SCREEN = 1
        const val MY_PAGE_SCREEN = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userInfo = intent.getParcelableExtra<UserInfo>(USER_INFO)
        setContent {
            NOWSOPTAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(userInfo)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scaffold(userInfo: UserInfo?) {
    var presses by remember { mutableIntStateOf(0) }
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        BottomNavigationItem(
            icon = Icons.Filled.Home,
            label = HOME
        ),
        BottomNavigationItem(
            icon = Icons.Filled.Search,
            label = SEARCH
        ),
        BottomNavigationItem(
            icon = Icons.Filled.Person,
            label = MY_PAGE
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("NOW SOPT")
                }
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            when(selectedItem) {
                HOME_SCREEN -> {
                    HomeScreen()
                }
                SEARCH_SCREEN -> {
                    SearchScreen()
                }
                MY_PAGE_SCREEN -> {
                    userInfo?.let {
                        MyPageScreen(userInfo = it)
                    }
                }
            }

        }
    }
}