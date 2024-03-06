package com.rikatech.admoblibrary.callback

import com.google.android.gms.ads.AdValue

interface RewardAdCallback {
    fun onAdClosed()
    fun onAdShowed()
    fun onAdFail(message: String?)
    fun onEarned()
    fun onPaid(adValue: AdValue, adUnitAds: String?)
}
