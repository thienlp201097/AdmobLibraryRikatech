package com.vapp.admobexample.utilsdemp

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.vapp.admoblibrary.AdsInterCallBack
import com.vapp.admoblibrary.ads.AdCallBackInterLoad
import com.vapp.admoblibrary.ads.AdmodUtils
import com.vapp.admoblibrary.ads.AppOpenManager
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
        AdmodUtils.getInstance().loadAndGetAdInterstitial(context, interHolder, object :
            AdCallBackInterLoad {
            override fun onAdClosed() {

            }

            override fun onEventClickAdClosed() {

            }

            override fun onAdShowed() {

            }

            override fun onAdLoaded(interstitialAd: InterstitialAd?, isLoading: Boolean) {
                interHolder.inter = interstitialAd
                interHolder.check = isLoading
            }

            override fun onAdFail(isLoading: Boolean) {
                interHolder.check = isLoading
            }

        })
    }

    fun showAdInter(
        context: Context,
        interHolder: InterHolder,
        callback: AdListener,
    ) {
        AppOpenManager.getInstance().isAppResumeEnabled = true
        AdmodUtils.getInstance()
            .showAdInterstitialWithCallbackNotLoadNew(context as Activity?, interHolder,10000, object :
                AdsInterCallBack {
                override fun onStartAction() {
                    callback.onAdClosed()

                }

                override fun onEventClickAdClosed() {
                    interHolder.inter = null
                    loadInter(context, interHolder)
                }

                override fun onAdShowed() {
                    AppOpenManager.getInstance().isAppResumeEnabled = false
                }

                override fun onAdLoaded() {

                }

                override fun onAdFail(error: String?) {
                    val log = error?.split(":")?.get(0)?.replace(" ","_")
                    callback.onFailed()
                }

                override fun onPaid(adValue: AdValue?) {

                }

            }, true)
    }

    fun loadAndShowIntersial(activity: Activity,adListener: AdListener){
        AdmodUtils.getInstance().loadAndShowAdInterstitialWithCallbackMultiAds(activity as AppCompatActivity?,"","",object : AdsInterCallBack{
            override fun onStartAction() {
            }

            override fun onEventClickAdClosed() {
            }

            override fun onAdShowed() {
            }

            override fun onAdLoaded() {
            }

            override fun onAdFail(error: String) {
                val log = error.split(":")[0].replace(" ","_")
                Log.d("===ADS",log)
            }

            override fun onPaid(adValue: AdValue?) {
            }
        },true)
    }
    interface AdListener {
        fun onAdClosed()
        fun onFailed()
    }
}
