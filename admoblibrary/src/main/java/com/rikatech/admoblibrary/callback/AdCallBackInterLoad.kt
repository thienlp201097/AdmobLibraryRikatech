package com.rikatech.admoblibrary.callback

import com.google.android.gms.ads.interstitial.InterstitialAd

interface AdCallBackInterLoad {
    fun onAdClosed()
    fun onEventClickAdClosed()
    fun onAdShowed()
    fun onAdLoaded(interstitialAd: InterstitialAd?, isLoading: Boolean)
    fun onAdFail(message: String?)
}
