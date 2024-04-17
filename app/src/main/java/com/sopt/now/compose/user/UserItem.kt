package com.sopt.now.compose.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.R

@Composable
fun UserItem(userInfo: UserInfo) {
    Row(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
            .background(Color(0xCCCDCCDD))
            .padding(end = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(id = R.drawable.carrot),
            contentDescription = null,
            modifier = Modifier
                .size(110.dp)
                .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = userInfo.nickname,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(10.dp))
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = userInfo.mbti,
            fontSize = 14.sp,
        )

    }
}

