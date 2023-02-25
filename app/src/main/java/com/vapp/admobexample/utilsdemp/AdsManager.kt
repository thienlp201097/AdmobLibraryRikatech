package com.vapp.admobexample.utilsdemp

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.vapp.admoblibrary.R
import com.vapp.admoblibrary.ads.AdCallBackInterLoad
import com.vapp.admoblibrary.ads.AdCallbackNew
import com.vapp.admoblibrary.ads.AdmodUtils
import com.vapp.admoblibrary.utils.Utils

object AdsManager {
    var interAds1: InterstitialAd? = null
    val mutable_inter1: MutableLiveData<InterstitialAd> = MutableLiveData()
    var check_inter1 = false
    fun loadInter(context: Context) {
        check_inter1 = true
        AdmodUtils.getInstance().loadAndGetAdInterstitial(
            context,
            context.getString(R.string.test_ads_admob_inter_id),
            context.getString(R.string.test_ads_admob_inter_id),
            object : AdCallBackInterLoad {
                override fun onAdClosed() {
                    Utils.getInstance().showMessenger(context, "onAdClosed")
                }

                override fun onEventClickAdClosed() {
                    Utils.getInstance().showMessenger(context, "onEventClickAdClosed")
                }

                override fun onAdShowed() {
                    Utils.getInstance().showMessenger(context, "onAdShowed")
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd, isLoad: Boolean) {
                    interAds1 = interstitialAd
                    check_inter1 = isLoad
                    Utils.getInstance().showMessenger(context, "onAdLoaded")
                }

                override fun onAdFail(isLoad: Boolean) {
                    check_inter1 = isLoad
                    Utils.getInstance().showMessenger(context, "onAdFail")
                }
            },
            mutable_inter1,
            check_inter1
        )
    }

    fun showInter(
        context: Context,
        interstitialAd: InterstitialAd?,
        adListener: AdListener,
        enableLoadingDialog: Boolean
    ) {
        AdmodUtils.getInstance().showAdInterstitialWithCallbackNotLoadNew(
            interstitialAd,
            context as Activity?,
            object : AdCallbackNew {
                override fun onAdLoaded() {
                    Utils.getInstance().showMessenger(context, "onAdLoaded")
                }

                override fun onAdClosed() {

                }

                override fun onAdFail() {
                    adListener.onFailed()
                    Utils.getInstance().showMessenger(context, "onAdFail")
                }

                override fun onEventClickAdClosed() {
                    adListener.onAdClosed()
                    Utils.getInstance().showMessenger(context, "onEventClickAdClosed")
                }

                override fun onAdShowed() {
                    Utils.getInstance().showMessenger(context, "onAdShowed")
                }
            }, enableLoadingDialog, mutable_inter1, check_inter1
        )
    }

    interface AdListener {
        fun onAdClosed()
        fun onFailed()
    }
}
