package com.example.mindbodycountrylist.data.retrofit

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class OkHttpClientProvider(private val networkConnectionInterceptor: NetworkConnectionInterceptor) {
    fun get(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addLoggingInterceptor()

        httpClientBuilder.addInterceptor(networkConnectionInterceptor)
        return httpClientBuilder.build()
    }
}