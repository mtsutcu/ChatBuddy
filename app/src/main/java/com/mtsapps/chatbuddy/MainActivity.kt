package com.mtsapps.chatbuddy

import android.content.Intent
import android.net.*
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.mtsapps.chatbuddy.databinding.ActivityMainBinding
import com.mtsapps.chatbuddy.ui.homefragment.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint


private const val AD_UNIT_ID = "ca-app-pub-7860825463372929/8799724174"


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var interstitialAd: InterstitialAd? = null
    private var adIsLoading: Boolean = false
    private var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ChatBuddy)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        Log.e("main", "main çalıştı")
        MobileAds.initialize(this) {}
        val view = binding.root
        setContentView(view)


        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(listOf("ABCDEF012345")).build()
        )


    }

    private fun loadAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            AD_UNIT_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, "${adError.message}")
                    interstitialAd = null
                    adIsLoading = false
                    val error =
                        "domain: ${adError.domain}, code: ${adError.code}, " + "message: ${adError.message}"
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.checkInternet),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    Log.e("domainerror", "$error")
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    ad.show(this@MainActivity)
                    adIsLoading = false
                }
            }
        )
    }


    fun startAd() {
        if (!adIsLoading && interstitialAd == null) {
            adIsLoading = true
            loadAd()


        }


    }
}





