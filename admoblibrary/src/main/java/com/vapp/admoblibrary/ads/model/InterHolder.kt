package com.vapp.admoblibrary.ads.model

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.ads.interstitial.InterstitialAd

open class InterHolder(var ads: String,var ads2: String) {
    var inter: InterstitialAd? = null
    val mutable: MutableLiveData<InterstitialAd> = MutableLiveData()
    var check = false
}