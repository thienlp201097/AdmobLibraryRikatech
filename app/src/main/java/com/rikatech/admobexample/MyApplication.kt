package com.rikatech.admobexample

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.rikatech.admoblibrary.ads.AppOpenManager

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            AppOpenManager.getInstance().isShowingAdsOnResumeBanner = true
            AppOpenManager.getInstance().isShowingAdsOnResume = true
        }
    }
}
