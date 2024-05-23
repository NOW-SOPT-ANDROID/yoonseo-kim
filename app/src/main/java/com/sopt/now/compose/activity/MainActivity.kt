package com.sopt.now.compose.activity

import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sopt.now.compose.BottomNavigationItem
import com.sopt.now.compose.ServicePool
import com.sopt.now.compose.home.HomeScreen
import com.sopt.now.compose.mypage.MyPageScreen
import com.sopt.now.compose.response.ResponseUserInfoDto
import com.sopt.now.compose.search.SearchScreen
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = intent.getStringExtra("userId")?.toInt()
        setContent {
            NOWSOPTAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    userId?.let {
                        Scaffold(userId = userId.toInt())
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scaffold(userId: Int) {
    val context = LocalContext.current
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        BottomNavigationItem(
            icon = Icons.Filled.Home,
            label = "Home"
        ),
        BottomNavigationItem(
            icon = Icons.Filled.Search,
            label = "Search"
        ),
        BottomNavigationItem(
            icon = Icons.Filled.Person,
            label = "My Page"
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
                0 -> {
                    HomeScreen()
                }
                1 -> {
                    SearchScreen()
                }
                2 -> {
                    MyPageScreen(context, userId)
                }
            }
        }
    }
}

private fun getUserInfo(userId: Int, onResult: (ResponseUserInfoDto.Data) -> Unit) {
    ServicePool.userService.getUserInfo(userId).enqueue(object : Callback<ResponseUserInfoDto> {
        override fun onResponse(
            call: Call<ResponseUserInfoDto>,
            response: Response<ResponseUserInfoDto>,
        ) {
            if (response.isSuccessful) {
                val data: ResponseUserInfoDto? = response.body()
                data?.data?.let {
                    onResult(it)
                }
                Log.d("MyPage", "data: $data, userId: $userId")
            } else {
                val error = response.errorBody()?.string() ?: response.message()
                Log.d("MyPage", "error: $error")
            }
        }

        override fun onFailure(call: Call<ResponseUserInfoDto>, t: Throwable) {
            Log.d("MyPage", "onFailure", t)
        }
    })
}