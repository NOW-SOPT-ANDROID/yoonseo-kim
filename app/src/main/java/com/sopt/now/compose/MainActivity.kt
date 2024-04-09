package com.sopt.now.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class MainActivity : ComponentActivity() {

    companion object {
        const val USER_INFO = "user_info"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                Surface {
                    val userInfo = intent.getParcelableExtra<UserInfo>(USER_INFO)
                    Main(userInfo)
                }
            }
        }
    }
}

@Composable
fun Main(userInfo: UserInfo?) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp),
    ) {
        userInfo?.let { user ->
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = null,
                modifier = Modifier.size(110.dp).aspectRatio(1f)
            )
            UserInfoItem(title = "닉네임", content = user.nickname)
            UserInfoItem(title = "MBTI", content = user.mbti)
            UserInfoItem(title = "ID", content = user.id)
            UserInfoItem(title = "비밀번호", content = user.password)
        }
    }
}

@Composable
fun UserInfoItem(title: String, content: String) {
    Text(
        text = "$title $content",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewMainActivityContent() {
    val userInfo = UserInfo ("", "", "", "")
    NOWSOPTAndroidTheme {
        Main(userInfo)
    }
}