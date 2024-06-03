package com.sopt.now.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
//    private const val BASE_URL = "http://34.64.233.12:8080/"
//
//    private val okHttpClient = OkHttpClient.Builder()
//        .addInterceptor { chain ->
//            val originalRequest = chain.request()
//            val newRequest = originalRequest.newBuilder()
//                .addHeader("memberId", "1")
//                .build()
//            chain.proceed(newRequest)
//        }
//        .build()
//
//    private val retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .client(okHttpClient)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    val userService: UserService by lazy {
//        retrofit.create(UserService::class.java)
//    }
}