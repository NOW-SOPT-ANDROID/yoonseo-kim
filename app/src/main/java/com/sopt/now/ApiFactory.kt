package com.sopt.now

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sopt.now.BuildConfig.AUTH_BASE_URL
import com.sopt.now.BuildConfig.OPEN_BASE_URL
import com.sopt.now.service.AuthService
import com.sopt.now.service.FriendService
import com.sopt.now.service.UserService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object ApiFactory {
    private val jsonConverterFactory = Json { ignoreUnknownKeys = true }
        .asConverterFactory("application/json".toMediaType())

    val authRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(AUTH_BASE_URL)
            .addConverterFactory(jsonConverterFactory)
            .build()
    }

    val openRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(OPEN_BASE_URL)
            .addConverterFactory(jsonConverterFactory)
            .build()
    }

    inline fun <reified T> create(baseUrl: String): T {
        val retrofit = when(baseUrl) {
            AUTH_BASE_URL -> authRetrofit
            OPEN_BASE_URL -> openRetrofit
            else -> throw IllegalArgumentException("Unknown Base URL")
        }
        return retrofit.create(T::class.java)
    }
}

object ServicePool {
    val userService: UserService by lazy { ApiFactory.create(AUTH_BASE_URL) }
    val authService: AuthService by lazy { ApiFactory.create(AUTH_BASE_URL) }
    val friendService: FriendService by lazy { ApiFactory.create(OPEN_BASE_URL) }
}