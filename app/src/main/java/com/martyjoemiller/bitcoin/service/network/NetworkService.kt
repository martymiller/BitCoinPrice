package com.martyjoemiller.bitcoin.service.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {

    lateinit var networkApi: NetworkApi

    fun init() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        networkApi = retrofit.create(NetworkApi::class.java)
    }

    fun getApi() = networkApi
}