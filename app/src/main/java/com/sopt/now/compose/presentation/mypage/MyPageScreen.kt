package com.sopt.now.compose.presentation.mypage

import android.content.Intent
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sopt.now.compose.R
import com.sopt.now.compose.presentation.login.LoginActivity
import com.sopt.now.compose.presentation.main.MainViewModel
import com.sopt.now.compose.presentation.user.UserInfo

@Composable
fun MyPageScreen() {

    val context = LocalContext.current

    val viewModel: MainViewModel = viewModel()
    val userInfo by viewModel.userInfo.observeAsState()
    val userInfoState = remember { mutableStateOf<UserInfo?>(null) }

    userInfo?.let {
        userInfoState.value = UserInfo(
            nickname = it.nickname,
            phone = it.phone,
            authenticationId = it.authenticationId
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp),
    ) {
        userInfoState.value?.let { user ->
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

@Composable
fun UserInfoItem(title: String, content: String) {
    Text(
        text = "$title   $content",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth()
    )
}
