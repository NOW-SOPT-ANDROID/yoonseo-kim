package com.sopt.now.compose.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.R
import com.sopt.now.compose.activity.LoginActivity.Companion.USER_INFO
import com.sopt.now.compose.activity.SignUpActivity.Companion.MAX_ID_LENGTH
import com.sopt.now.compose.activity.SignUpActivity.Companion.MAX_PWD_LENGTH
import com.sopt.now.compose.activity.SignUpActivity.Companion.MIN_ID_LENGTH
import com.sopt.now.compose.activity.SignUpActivity.Companion.MIN_PWD_LENGTH
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import com.sopt.now.compose.user.UserInfo

class SignUpActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val userId = intent.getStringExtra("userId").toString()
                    val userPassword = intent.getStringExtra("userPassword").toString()
                    val userNickname = intent.getStringExtra("userNickname").toString()
                    val userMbti = intent.getStringExtra("userMbti").toString()
                    SignUp(userId, userPassword, userNickname, userMbti)
                }
            }
        }
    }

    companion object {
        const val USER_INFO = "user_info"
        const val MIN_ID_LENGTH = 6
        const val MAX_ID_LENGTH = 10
        const val MIN_PWD_LENGTH = 8
        const val MAX_PWD_LENGTH = 12
    }
}

fun isSignUpAvailable(context: Context, id: String, password: String, nickname: String): Boolean {
    when {
        id.length !in MIN_ID_LENGTH..MAX_ID_LENGTH -> Toast.makeText(context, context.getString(R.string.id_wrong_message), Toast.LENGTH_SHORT).show()
        password.length !in MIN_PWD_LENGTH..MAX_PWD_LENGTH -> Toast.makeText(context, context.getString(R.string.pwd_wrong_message), Toast.LENGTH_SHORT).show()
        nickname.isBlank() || nickname.contains(" ") -> Toast.makeText(context, context.getString(R.string.nickname_wrong_message), Toast.LENGTH_SHORT).show()
        else -> {
            Toast.makeText(context, context.getString(R.string.sign_up_success_message), Toast.LENGTH_SHORT).show()
            return true
        }
    }
    return false
}

@Composable
fun SignUp(userId: String, userPassword: String, userNickname: String, userMbti: String) {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var mbti by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text (
            text = stringResource(id = R.string.sign_up_title),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = stringResource(id = R.string.id),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = id,
            onValueChange = { id = it },
            placeholder = { Text(stringResource(id = R.string.id_hint)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = stringResource(id = R.string.pwd),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text(stringResource(id = R.string.pwd_hint)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = stringResource(id = R.string.nickname),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = nickname,
            onValueChange = { nickname = it },
            placeholder = { Text(stringResource(id = R.string.nickname_hint)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = stringResource(id = R.string.mbti),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = mbti,
            onValueChange = { mbti = it },
            placeholder = { Text(stringResource(id = R.string.mbti_hint)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                if (isSignUpAvailable(context, id, password, nickname)) {
                    val userInfo = UserInfo(id, password, nickname, mbti)
                    val intent = Intent(context, LoginActivity::class.java).apply {
                        putExtra(USER_INFO, userInfo)
                    }
                    context.startActivity(intent)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.btn_sign_up),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun SignUpPreview() {
//    NOWSOPTAndroidTheme {
//        SignUp("", "", "", "")
//    }
//}