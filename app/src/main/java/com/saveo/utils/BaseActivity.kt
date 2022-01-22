package com.saveo.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.saveo.R

abstract class BaseActivity : AppCompatActivity() {

    inline fun delayHandler(delayedMilliSec: Long, crossinline func: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            func()
        }, delayedMilliSec)
    }

    fun apiCall(
        defaultMsg: String = getString(R.string.required_internet),
        networkFun: () -> Unit,
    ) {
        if (isNetworkAvailable()) {
            networkFun()
        } else {
            Toast.makeText(this@BaseActivity, defaultMsg, Toast.LENGTH_SHORT).show()
        }
    }

    inline fun networkCheck(networkFun: () -> Unit, offlineCallback: () -> Unit) {
        if (isNetworkAvailable()) { networkFun() }
        else { offlineCallback() }
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return (networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false)
    }
}