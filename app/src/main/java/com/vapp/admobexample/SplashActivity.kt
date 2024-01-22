package com.vapp.admobexample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdValue
import com.vapp.admobexample.utilsdemp.AdsManager.interholder
import com.vapp.admobexample.view.MainActivity
import com.vapp.admobexample.view.OtherActivity
import com.vapp.admoblibrary.AdsInterCallBack
import com.vapp.admoblibrary.ads.AOAManager
import com.vapp.admoblibrary.ads.AdmodUtils.initAdmob
import com.vapp.admoblibrary.ads.AdmodUtils.loadAndShowAdInterstitial
import com.vapp.admoblibrary.ads.AppOpenManager
import com.vapp.admoblibrary.cmp.GoogleMobileAdsConsentManager
import com.vapp.admoblibrary.utils.Utils
import java.util.concurrent.atomic.AtomicBoolean

class SplashActivity : AppCompatActivity() {
    var aoaManager: AOAManager? = null
    var isAOAFalse = false
    private var isMobileAdsInitializeCalled = AtomicBoolean(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        if (!isTaskRoot && intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intent.action != null && intent.action === Intent.ACTION_MAIN) {
            finish()
            return
        }
        setupCMP()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        System.exit(0)
    }

    override fun onResume() {
        super.onResume()
        if (isAOAFalse) {
            Utils.getInstance().replaceActivity(this@SplashActivity, MainActivity::class.java)
        }
    }

    fun setupCMP(){
        val googleMobileAdsConsentManager = GoogleMobileAdsConsentManager(this)
        googleMobileAdsConsentManager.gatherConsent { error ->
            error?.let {
                // Consent not obtained in current session.
                initializeMobileAdsSdk()
            }

            if (googleMobileAdsConsentManager.canRequestAds) {
                initializeMobileAdsSdk()
            }
        }

    }

    private fun initializeMobileAdsSdk() {
        if (isMobileAdsInitializeCalled.get()) {
            //start action
            return
        }
        isMobileAdsInitializeCalled.set(true)
        initAdmob(this, 10000, isDebug = true, isEnableAds = true)
        AppOpenManager.getInstance().init(application, getString(R.string.test_ads_admob_app_open))
        AppOpenManager.getInstance().disableAppResumeWithActivity(SplashActivity::class.java)
        showInter()

    }

    fun showInter(){
        loadAndShowAdInterstitial(this@SplashActivity, interholder.ads, object : AdsInterCallBack {
            override fun onStartAction() {}
            override fun onEventClickAdClosed() {
                Utils.getInstance().addActivity(
                    this@SplashActivity,
                    MainActivity::class.java
                )
            }

            override fun onAdShowed() {}
            override fun onAdLoaded() {}
            override fun onAdFail(error: String) {
                Utils.getInstance().addActivity(
                    this@SplashActivity,
                    MainActivity::class.java
                )
            }

            override fun onPaid(adValue: AdValue, adUnitAds: String) {
                }
        }, false)
    }

    fun showAOA(){
        aoaManager = AOAManager(
            this,
            "ca-app-pub-3940256099942544/3419835294",
            20000,
            object : AOAManager.AppOpenAdsListener {
                override fun onAdPaid(adValue: AdValue, s: String) {
                    Toast.makeText(
                        this@SplashActivity,
                        "\${adValue?.currencyCode}|\${adValue?.valueMicros}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAdsClose() {
                    Utils.getInstance()
                        .replaceActivity(this@SplashActivity, MainActivity::class.java)
                }

                override fun onAdsFailed(massage: String) {
                    isAOAFalse = true
                    Utils.getInstance()
                        .replaceActivity(this@SplashActivity, MainActivity::class.java)
                }
            })
        aoaManager!!.loadAndShowAoA()
    }
}
