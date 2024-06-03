package com.sopt.now.compose.presentation.friend

import androidx.compose.foundation.Image
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.sopt.now.compose.data.dto.response.ResponseFriendDto

@OptIn(ExperimentalCoilApi::class)
@Composable
fun FriendItem(friend: ResponseFriendDto.Data) {
    Row(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .padding(end = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = rememberImagePainter(data = friend.avatar),
            contentDescription = "Friend Profile",
            modifier = Modifier
                .size(80.dp)
                .padding(start = 10.dp)
                .padding(vertical = 10.dp)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = friend.firstName,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(10.dp))
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = friend.email,
            fontSize = 14.sp,
        )

    }
}