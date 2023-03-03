package com.vapp.admobexample.utilsdemp

import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.vapp.admoblibrary.AdsInterCallBack
import com.vapp.admoblibrary.ads.AdCallBackInterLoad
import com.vapp.admoblibrary.ads.AdmodUtils
import com.vapp.admoblibrary.ads.model.InterHolder
import com.vapp.admoblibrary.ads.model.NativeHolder
import com.vapp.admoblibrary.utils.Utils

object AdsManager {
    var interAds1: InterstitialAd? = null
    val mutable_inter1: MutableLiveData<InterstitialAd> = MutableLiveData()
    var check_inter1 = false

    var nativeHolder = NativeHolder("ca-app-pub-3940256099942544/2247696110", "ca-app-pub-3940256099942544/2247696110")
    var interholder = InterHolder("ca-app-pub-3940256099942544/1033173712","ca-app-pub-3940256099942544/1033173712")
    fun loadInter(context: Context, interHolder: InterHolder) {
        interHolder.check = true
        AdmodUtils.getInstance().loadAndGetAdInterstitial(context,interHolder,
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
                    interholder.inter = interstitialAd
                    interHolder.check = isLoad
                    Utils.getInstance().showMessenger(context, "onAdLoaded")
                }

                override fun onAdFail(isLoad: Boolean) {
                    interHolder.check = isLoad
                    Utils.getInstance().showMessenger(context, "onAdFail")
                }
            }
        )
    }

    fun showInter(
        context: Context,
        interHolder: InterHolder,
        adListener: AdListener,
        enableLoadingDialog: Boolean
    ) {
        AdmodUtils.getInstance().showAdInterstitialWithCallbackNotLoadNew(
            context as Activity?,interHolder,
            object : AdsInterCallBack {
                override fun onAdLoaded() {
                    Utils.getInstance().showMessenger(context, "onAdLoaded")
                }

                override fun onAdClosed() {

                }

                override fun onAdFail() {
                    interHolder.inter = null
                    loadInter(context,interHolder)
                    adListener.onFailed()
                    Utils.getInstance().showMessenger(context, "onAdFail")
                }

                override fun onPaid(adValue: AdValue?) {
                    Utils.getInstance().showMessenger(context, adValue.toString())
                }

                override fun onEventClickAdClosed() {
                    interHolder.inter = null
                    loadInter(context,interHolder)
                    adListener.onAdClosed()
                    Utils.getInstance().showMessenger(context, "onEventClickAdClosed")
                }

                override fun onAdShowed() {
                    Utils.getInstance().showMessenger(context, "onAdShowed")
                }
            }, enableLoadingDialog)
    }

    interface AdListener {
        fun onAdClosed()
        fun onFailed()
    }
}
