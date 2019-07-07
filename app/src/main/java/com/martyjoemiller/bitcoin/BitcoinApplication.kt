package com.martyjoemiller.bitcoin

import android.app.Application
import com.martyjoemiller.bitcoin.service.network.NetworkService

class BitcoinApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NetworkService.init()
    }
}