package com.martyjoemiller.bitcoin.service.network

import com.martyjoemiller.bitcoin.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {

    lateinit var networkApi: NetworkApi

    fun init() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coinbase.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
        networkApi = retrofit.create(NetworkApi::class.java)
    }

    private val client: OkHttpClient by lazy {
        val clientBuilder = OkHttpClient.Builder()

        if (BuildConfig.BUILD_TYPE == "debug") {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            clientBuilder.addInterceptor(loggingInterceptor)
        }
        return@lazy clientBuilder.build()
    }
}