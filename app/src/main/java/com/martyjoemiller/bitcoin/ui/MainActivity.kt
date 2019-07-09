package com.martyjoemiller.bitcoin.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.martyjoemiller.bitcoin.R
import com.martyjoemiller.bitcoin.service.network.NetworkService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    @SuppressLint("CheckResult")
    private fun init() {
        NetworkService.networkApi.getBitcoinPrice()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val price = it.data.amount
                    bitcoinPrice.text = "$$price"
                },
                {
                    bitcoinPrice.text = "Error"
                }
            )
    }
}


