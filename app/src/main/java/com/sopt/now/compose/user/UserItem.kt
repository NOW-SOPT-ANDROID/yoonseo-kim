package com.sopt.now.compose.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.R

@Composable
fun UserItem(userInfo: UserInfo) {
    Row(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_person_black_24),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
        )
        Text(
            text = "김윤서",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 18.dp)
                .align(Alignment.CenterVertically)
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = "안녕 나는 안드로이드 YB야 잘부탁해",
            modifier = Modifier
                .padding(end = 20.dp)
                .align(Alignment.CenterVertically)
        )

    }
}

