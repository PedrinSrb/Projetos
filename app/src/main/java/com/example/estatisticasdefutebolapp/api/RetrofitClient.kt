package com.example.estatisticasdefutebolapp.api

import com.example.estatisticasdefutebolapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL =
        "https://v3.football.api-sports.io/"

    private val client = OkHttpClient.Builder()
        .addInterceptor(Interceptor { chain ->

            val request = chain.request()
                .newBuilder()
                .addHeader(
                    "x-apisports-key",
                    BuildConfig.API_FOOTBALL_KEY
                )
                .build()

            chain.proceed(request)
        })
        .build()

    val instance: FootballApi by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(FootballApi::class.java)
    }
}