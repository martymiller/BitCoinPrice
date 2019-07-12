package com.martyjoemiller.bitcoin.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.martyjoemiller.bitcoin.R
import com.martyjoemiller.bitcoin.model.Price
import com.martyjoemiller.bitcoin.service.network.NetworkService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    var currentPrice: Float = 0F
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    @SuppressLint("CheckResult")
    private fun init() {
        disposable = Observable.interval(5, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::callBitcoinEndpoint, ::onError)
    }

    @SuppressLint("CheckResult")
    private fun callBitcoinEndpoint(aLong: Long) {
        NetworkService.networkApi.getBitcoinPrice()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleResults, ::handleError)
    }

    private fun onError(throwable: Throwable) {
        showError()
    }

    private fun handleResults(price: Price) {
        val newPrice = price.data.amount.toFloat()
        if(currentPrice != 0F) {
            if(newPrice != currentPrice) {
                if (newPrice < currentPrice) {
                    bitcoinParentView.setBackgroundColor(Color.RED)
                } else {
                    bitcoinParentView.setBackgroundColor(Color.GREEN)
                }
            }
        }
        bitcoinPrice.text = "$${price.data.amount}"
        currentPrice = newPrice
    }

    private fun handleError(throwable: Throwable) {
        showError()
    }

    private fun showError() {
        bitcoinParentView.setBackgroundColor(Color.BLACK)
        bitcoinPrice.text = "Unable to Retrieve Price"
    }
}


