package com.rikatech.admoblibrary.callback

import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.nativead.NativeAd

interface NativeAdCallback {
    fun onLoadedAndGetNativeAd(ad: NativeAd?)
    fun onNativeAdLoaded()
    fun onAdFail(error: String)
    fun onAdPaid(adValue: AdValue?, adUnitAds: String?)
}
