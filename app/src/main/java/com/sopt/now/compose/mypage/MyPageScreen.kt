package com.sopt.now.compose.mypage

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.R
import com.sopt.now.compose.ServicePool.userService
import com.sopt.now.compose.activity.LoginActivity
import com.sopt.now.compose.response.ResponseUserInfoDto
import com.sopt.now.compose.user.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MyPageScreen(context: Context, userId: Int) {

    val context = LocalContext.current

    // 사용자 정보 상태를 관리하기 위한 변수 선언
    var userInfo by remember { mutableStateOf<UserInfo?>(null) }

    // 기존 userInfo 파라미터가 null이 아니면 상태를 업데이트
    LaunchedEffect(userId) {
        getUserInfo(userId) { userInfoDto ->
            userInfo = UserInfo(
                nickname = userInfoDto.nickname,
                phone = userInfoDto.phone,
                authenticationId = userInfoDto.authenticationId
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp),
    ) {
        userInfo?.let { user ->
            Image(
                painter = painterResource(id = R.drawable.donut),
                contentDescription = null,
                modifier = Modifier.size(110.dp).aspectRatio(1f)
            )
            UserInfoItem(title = "닉네임", content = user.nickname)
            UserInfoItem(title = "Phone", content = user.phone)
            UserInfoItem(title = "ID", content = user.authenticationId)
        }

        Spacer(modifier = Modifier.height(120.dp))

        Button(
            onClick = {
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            }, modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.btn_logout),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

}

private fun getUserInfo(userId: Int, onResult: (ResponseUserInfoDto.Data) -> Unit) {
    userService.getUserInfo(userId).enqueue(object : Callback<ResponseUserInfoDto> {
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

@Composable
fun UserInfoItem(title: String, content: String) {
    Text(
        text = "$title   $content",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth()
    )
}
