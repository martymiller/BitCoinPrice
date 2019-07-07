package com.martyjoemiller.bitcoin.service.network

import retrofit2.http.GET

interface NetworkApi {

    @GET("user")
    fun getUser()
}