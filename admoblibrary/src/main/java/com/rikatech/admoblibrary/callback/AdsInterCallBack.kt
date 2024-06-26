package com.rikatech.admoblibrary.callback

import com.google.android.gms.ads.AdValue

interface AdsInterCallBack {
    fun onStartAction()
    fun onEventClickAdClosed()
    fun onAdShowed()
    fun onAdLoaded()
    fun onAdFail(error: String?)
    fun onPaid(adValue: AdValue, adUnitAds: String?)
}
