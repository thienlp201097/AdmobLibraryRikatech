package com.vapp.admoblibrary.ads

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.ironsource.mediationsdk.ISBannerSize
import com.ironsource.mediationsdk.IronSource
import com.ironsource.mediationsdk.logger.IronSourceError
import com.ironsource.mediationsdk.sdk.InterstitialListener
import com.ironsource.mediationsdk.IronSourceBannerLayout




object IronSourceUtil {
    fun initIronSource(activity: Activity,appKey:String){
        IronSource.init(activity,appKey)
    }
    val TAG:String = "IronSourceUtil"
    fun showInterstitialAdsWithCallback(adPlacementId:String, showLoadingDialog:Boolean, callback:AdCallback){
        val mInterstitialListener = object : InterstitialListener {
            override fun onInterstitialAdReady() {
                IronSource.showInterstitial(adPlacementId)
                IronSource.removeInterstitialListener()
            }

            override fun onInterstitialAdLoadFailed(p0: IronSourceError?) {
                callback.onAdFail()
            }

            override fun onInterstitialAdOpened() {
                Log.d(TAG,"onInterstitialAdOpened")
            }

            override fun onInterstitialAdClosed() {
                callback.onAdClosed()
            }

            override fun onInterstitialAdShowSucceeded() {
                Log.d(TAG,"onInterstitialAdShowSucceeded")
            }

            override fun onInterstitialAdShowFailed(p0: IronSourceError?) {
                callback.onAdFail()
            }

            override fun onInterstitialAdClicked() {
                Log.d(TAG,"onInterstitialAdClicked")
            }
        }
        if(IronSource.isInterstitialReady()){
            IronSource.showInterstitial(adPlacementId)
        }
        else{
            IronSource.loadInterstitial()
        }
        IronSource.setInterstitialListener(mInterstitialListener);
    }
    fun showBanner(activity:Activity, bannerContainer: ViewGroup, adPlacementId:String, bannerSize:ISBannerSize){
        val banner = IronSource.createBanner(activity, bannerSize)
        bannerContainer.addView(banner)
        IronSource.loadBanner(banner,adPlacementId)
    }
}