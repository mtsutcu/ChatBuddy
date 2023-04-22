package com.mtsapps.chatbuddy.di

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.appopen.AppOpenAd
import com.mtsapps.chatbuddy.AppOpenManager
import com.mtsapps.chatbuddy.MainActivity
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiltApplication : Application() {


    private var appOpenManager: AppOpenManager? = null
    override fun onCreate() {
        super.onCreate()

        MobileAds.initialize(this) {
            Log.d("tag", "MobileAds init ")
        }
        appOpenManager = AppOpenManager(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(appOpenManager!!.defaultLifecycleObserver)

    }
}
