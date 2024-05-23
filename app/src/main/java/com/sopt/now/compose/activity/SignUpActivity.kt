package com.sopt.now.compose.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.R
import com.sopt.now.compose.ServicePool
import com.sopt.now.compose.request.RequestSignUpDto
import com.sopt.now.compose.response.ResponseSignUpDto
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignUpPage()
                }
            }
        }
    }
}

@Composable
fun SignUpPage() {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

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
            text = stringResource(id = R.string.phone),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = phone,
            onValueChange = { phone = it },
            placeholder = { Text(stringResource(id = R.string.phone_hint)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                signUp(context, id, password, nickname, phone)
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

fun signUp(context: Context, id: String, password: String, nickname: String, phone: String) {
    val signUpRequest = RequestSignUpDto(authenticationId = id, password = password, nickname = nickname, phone = phone)
    ServicePool.authService.signUp(signUpRequest).enqueue(object : Callback<ResponseSignUpDto> {
        override fun onResponse(call: Call<ResponseSignUpDto>, response: Response<ResponseSignUpDto>) {
            if (response.isSuccessful) {
                val data: ResponseSignUpDto? = response.body()
                val userId = response.headers()["location"]
                Toast.makeText(context, "회원가입 성공 유저의 ID는 $userId 입니둥", Toast.LENGTH_SHORT).show()
                Log.d("Login", "data: $data, userId: $userId")
                if (context is Activity) {
                    navigateToLogin(userId, context)
                }
            } else {
                val error = response.message()
                Toast.makeText(context, "로그인 실패: $error", Toast.LENGTH_SHORT).show()
            }
        }
        override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
            Toast.makeText(context, "서버 에러 발생", Toast.LENGTH_SHORT).show()
        }
    })
}

private fun navigateToLogin(userId: String?, activity: Activity) {
    val intent = Intent(activity, LoginActivity::class.java).apply {
        putExtra("userId", userId)
    }
    activity.startActivity(intent)
    activity.finish()
}
