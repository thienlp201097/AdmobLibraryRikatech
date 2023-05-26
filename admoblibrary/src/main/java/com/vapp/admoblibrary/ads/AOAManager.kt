package com.vapp.admoblibrary.ads

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Window
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.vapp.admoblibrary.R

class AOAManager(private val activity: Activity, val id : String,val timeOut: Long, val appOpenAdsListener: AppOpenAdsListener) {

    private var appOpenAd: AppOpenAd? = null
    private var loadCallback: AppOpenAd.AppOpenAdLoadCallback? = null
    var isShowingAd = false
    var dialogFullScreen: Dialog? = null
    private val adRequest: AdRequest
        get() = AdRequest.Builder().build()

    private val isAdAvailable: Boolean
        get() = appOpenAd != null

    fun loadAndShowAoA() {
        var idAoa = id
        if (AdmodUtils.isTesting){
             idAoa = activity.getString(R.string.test_ads_admob_app_open)
        }
        val handler = Handler(Looper.getMainLooper())
        //Check timeout show inter
        isShowingAd = false
        val runnable = Runnable {
            if (!isShowingAd) {
                isShowingAd = true
                if (AppOpenManager.getInstance().isInitialized) {
                    AppOpenManager.getInstance().isAppResumeEnabled = true
                }
                appOpenAdsListener.onAdClosedOrFail()
            }
        }
        handler.postDelayed(runnable, timeOut)
        if (isAdAvailable) {
            handler.removeCallbacksAndMessages(null)
            appOpenAdsListener.onAdClosedOrFail()
            return
        } else {
            Log.d("tag", "fetching... ")
            loadCallback = object : AppOpenAd.AppOpenAdLoadCallback() {

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    handler.removeCallbacksAndMessages(null)
                    appOpenAdsListener.onAdClosedOrFail()
                    Log.d("tag", "onAppOpenAdFailedToLoad: ")
                }

                override fun onAdLoaded(ad: AppOpenAd) {
                    handler.removeCallbacksAndMessages(null)
                    super.onAdLoaded(ad)
                    appOpenAd = ad
                    Log.d("tag", "isAdAvailable = true")
                    showAdIfAvailable()
                }
            }
            val request = adRequest
            AppOpenAd.load(activity, idAoa, request, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback!!)
        }
    }

    fun showAdIfAvailable() {
        Log.d("tag", "$isShowingAd - $isAdAvailable")

        if (!isShowingAd && isAdAvailable) {
            Log.d("tag", "will show ad ")
            val fullScreenContentCallback: FullScreenContentCallback =
                object : FullScreenContentCallback() {

                    override fun onAdDismissedFullScreenContent() {
                        dialogFullScreen?.dismiss()
                        appOpenAd = null
                        isShowingAd = true
                        appOpenAdsListener.onAdClosedOrFail()
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        dialogFullScreen?.dismiss()
                        isShowingAd = true
                        appOpenAdsListener.onAdClosedOrFail()
                    }

                    override fun onAdShowedFullScreenContent() {
                        isShowingAd = true
                    }
                }
            appOpenAd?.run {
                this.fullScreenContentCallback = fullScreenContentCallback
                dialogFullScreen = Dialog(activity)
                dialogFullScreen?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialogFullScreen?.setContentView(R.layout.dialog_full_screen)
                dialogFullScreen?.setCancelable(false)
                dialogFullScreen?.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
                dialogFullScreen?.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
                if (!activity.isFinishing) {
                    dialogFullScreen?.show()
                }
                Handler().postDelayed({
                    show(activity)
                },800)
            }
        }
    }

    interface AppOpenAdsListener {
        fun onAdClosedOrFail()
    }

}