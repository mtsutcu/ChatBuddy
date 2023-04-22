package com.mtsapps.chatbuddy

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.mtsapps.chatbuddy.di.HiltApplication

class AppOpenManager(private val myApplication: HiltApplication) :
    Application.ActivityLifecycleCallbacks,
    LifecycleObserver {


    private var appOpenAd: AppOpenAd? = null
    private var loadCallback: AppOpenAd.AppOpenAdLoadCallback? = null
    private var currentActivity: Activity? = null
    private var isShowingAd = false
    private val adRequest: AdRequest
        get() = AdRequest.Builder().build()

    private val isAdAvailable: Boolean
        get() = appOpenAd != null

    companion object {
        private const val AD_UNIT_ID = "ca-app-pub-3940256099942544/3419835294" //test id
    }

    init {
        myApplication.registerActivityLifecycleCallbacks(this)
    }


    private fun fetchAd() {
        if (isAdAvailable) {
            return
        } else {
            Log.d("tag", "fetching... ")
            loadCallback = object : AppOpenAd.AppOpenAdLoadCallback() {

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    Log.d("tag", "onAppOpenAdFailedToLoad: ")
                }

                override fun onAdLoaded(ad: AppOpenAd) {
                    super.onAdLoaded(ad)
                    appOpenAd = ad
                    Log.d("tag", "isAdAvailable = true")
                    showAdIfAvailable()


                }
            }
            val request = adRequest
            AppOpenAd.load(
                myApplication,
                AD_UNIT_ID,
                request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                loadCallback!!
            )
        }
    }

    fun showAdIfAvailable() {
        Log.d("tag", "$isShowingAd - $isAdAvailable")

        if (!isShowingAd && isAdAvailable) {
            Log.d("tag", "will show ad ")
            val fullScreenContentCall: FullScreenContentCallback =
                object : FullScreenContentCallback() {

                    override fun onAdDismissedFullScreenContent() {
                        appOpenAd = null
                        isShowingAd = false

                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {

                    }

                    override fun onAdShowedFullScreenContent() {
                        isShowingAd = true
                    }
                }
            appOpenAd!!.fullScreenContentCallback = fullScreenContentCall
            currentActivity?.let { appOpenAd?.show(it) }
        } else {
            Log.d("tag", "can't show ad ")
            fetchAd()
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        currentActivity = activity
    }

    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        currentActivity = activity
    }

    override fun onActivityDestroyed(activity: Activity) {
        currentActivity = null
    }


    var defaultLifecycleObserver = object : DefaultLifecycleObserver {
        override fun onStart(owner: LifecycleOwner) {
            super.onStart(owner)
            Log.d("tag", "onStart()")
            fetchAd()

        }

        override fun onCreate(owner: LifecycleOwner) {
            super.onCreate(owner)
            Log.d("tag", "onCreate() ")

        }


    }

}