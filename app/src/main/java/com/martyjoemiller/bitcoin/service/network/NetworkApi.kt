package com.martyjoemiller.bitcoin.service.network

import com.martyjoemiller.bitcoin.model.Price
import io.reactivex.Observable
import retrofit2.http.GET

interface NetworkApi {

    @GET("v2/prices/BTC-USD/sell")
    fun getBitcoinPrice() : Observable<Price>
}