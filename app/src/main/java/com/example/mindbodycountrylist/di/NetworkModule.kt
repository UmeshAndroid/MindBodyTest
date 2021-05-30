package com.example.mindbodycountrylist.di

import android.content.Context
import androidx.annotation.NonNull
import com.example.mindbodycountrylist.*
import com.example.mindbodycountrylist.data.retrofit.NetworkConnectionInterceptor
import com.example.mindbodycountrylist.data.retrofit.OkHttpClientProvider
import com.example.mindbodycountrylist.data.retrofit.RetrofitProvider
import com.example.mindbodycountrylist.data.api.APIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideNetworkConnectionInterceptor(@ApplicationContext appContext: Context) =
        NetworkConnectionInterceptor(appContext)

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    fun provideOkHttpClientProvider(networkConnectionInterceptor: NetworkConnectionInterceptor) =
        OkHttpClientProvider(
            networkConnectionInterceptor
        )

    @Provides
    fun provideOKHttpClient(okHttpClientProvider: OkHttpClientProvider) = okHttpClientProvider.get()

    @Provides
    fun provideRetrofit(BASE_URL: String, okHttpClient: OkHttpClient) =
        RetrofitProvider(
            BASE_URL,
            okHttpClient = okHttpClient
        ).get()

    @Provides
    @Singleton
    fun provideApiService(@NonNull retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }
}