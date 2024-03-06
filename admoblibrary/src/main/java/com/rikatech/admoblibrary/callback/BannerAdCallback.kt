package com.rikatech.admoblibrary.callback

import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdValue

interface BannerAdCallback {
    fun onBannerAdLoaded(adSize: AdSize?)
    fun onAdFail()
    fun onAdPaid(adValue: AdValue?)
}
