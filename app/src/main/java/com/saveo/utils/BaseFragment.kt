package com.saveo.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.saveo.R

abstract class BaseFragment() : Fragment() {
    private var context1: AppCompatActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context1 = (requireActivity() as BaseActivity)
    }

    inline fun delayHandler(delayedMilliSec: Long, crossinline func: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            func()
        }, delayedMilliSec)
    }

    fun apiCall(
        defaultMsg: String = getString(R.string.required_internet),
        networkFun: () -> Unit,
    ) {
        (requireActivity() as BaseActivity).apiCall(defaultMsg, networkFun)
    }

    fun networkCheck(networkFun: () -> Unit, offlineCallback: () -> Unit) {
        (requireActivity() as BaseActivity).networkCheck(
            networkFun = { networkFun() },
            offlineCallback = { offlineCallback() })
    }

    fun isNetworkAvailable(): Boolean {
        return (requireActivity() as BaseActivity).isNetworkAvailable()
    }
}