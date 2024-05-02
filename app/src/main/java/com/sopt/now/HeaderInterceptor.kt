package com.sopt.now

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val memberId: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("memberId", memberId)
            .build()
        return chain.proceed(newRequest)
    }
}