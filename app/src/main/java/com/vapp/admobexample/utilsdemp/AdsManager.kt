package com.vapp.admobexample.utilsdemp

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.vapp.admobexample.view.OtherActivity
import com.vapp.admoblibrary.R
import com.vapp.admoblibrary.ads.AdCallBackInterLoad
import com.vapp.admoblibrary.ads.AdCallbackNew
import com.vapp.admoblibrary.ads.AdmodUtils
import com.vapp.admoblibrary.ads.AppOpenManager
import com.vapp.admoblibrary.utils.Utils

object AdsManager {
    var interstitial1: InterstitialAd? = null
    var interstitial2: InterstitialAd? = null
    var interstitial3: InterstitialAd? = null

    fun loadInter(context: Context){
        AdmodUtils.getInstance().loadAndGetAdInterstitial(
            context,
            context.getString(R.string.test_ads_admob_inter_id),context.getString(R.string.test_ads_admob_inter_id),
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

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    interstitial1 = interstitialAd
                    Utils.getInstance().showMessenger(context, "onAdLoaded")
                }

                override fun onAdFail() {
                    Utils.getInstance().showMessenger(context, "onAdFail")
                }
            }
        )
    }

    fun loadInter2(context: Context){
        AdmodUtils.getInstance().loadAndGetAdInterstitial(
            context,
            context.getString(R.string.test_ads_admob_inter_id),context.getString(R.string.test_ads_admob_inter_id),
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

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    interstitial2 = interstitialAd
                    Utils.getInstance().showMessenger(context, "onAdLoaded2")
                }

                override fun onAdFail() {
                    Utils.getInstance().showMessenger(context, "onAdFail")
                }
            }
        )
    }

    fun loadInter3(context: Context){
        AdmodUtils.getInstance().loadAndGetAdInterstitial(
            context,
            context.getString(R.string.test_ads_admob_inter_id),context.getString(R.string.test_ads_admob_inter_id),
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

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    interstitial3 = interstitialAd
                    Utils.getInstance().showMessenger(context, "onAdLoaded3")
                }

                override fun onAdFail() {
                    Utils.getInstance().showMessenger(context, "onAdFail")
                }
            }
        )
    }

    fun showInter(context: Context, interstitialAd: InterstitialAd?,adListener: AdListener, enableLoadingDialog : Boolean){
        AdmodUtils.getInstance().showAdInterstitialWithCallbackNotLoadNew(
            interstitialAd,
            context as Activity?,
            object : AdCallbackNew {
                override fun onAdLoaded() {
                    Utils.getInstance().showMessenger(context, "onAdLoaded")
                    Log.d("===Ads","4")
                }

                override fun onAdClosed() {

                }

                override fun onAdFail() {
                    Log.d("===Ads","3")
                    adListener.onFailed()
                    Utils.getInstance().showMessenger(context, "onAdFail")
                }

                override fun onEventClickAdClosed() {
                    Log.d("===Ads","2")
                    adListener.onAdClosed()
                    Utils.getInstance().showMessenger(context, "onEventClickAdClosed")
                }

                override fun onAdShowed() {
                    Log.d("===Ads","1")
                    Utils.getInstance().showMessenger(context, "onAdShowed")
                }
            },enableLoadingDialog)
    }

    fun loadAndShowInter(context: Context,adListener: AdListener){
        AdmodUtils.getInstance().loadGetAndShowInterstitial(
            context,
            context.getString(R.string.test_ads_admob_inter_id),context.getString(R.string.test_ads_admob_inter_id),
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

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    interstitial1 = interstitialAd
                    AdmodUtils.getInstance().showAdInterstitial(
                        interstitial1,
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
                        },false)
                    Utils.getInstance().showMessenger(context, "onAdLoaded")
                }

                override fun onAdFail() {
                    Utils.getInstance().showMessenger(context, "onAdFail")
                }
            },true
        )
    }

    interface AdListener {
        fun onAdClosed()
        fun onFailed()
    }
}
