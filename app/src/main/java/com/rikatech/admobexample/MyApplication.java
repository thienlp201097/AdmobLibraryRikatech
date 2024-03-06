package com.rikatech.admobexample;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.rikatech.admoblibrary.ads.AppOpenManager;

public class MyApplication extends Application {
    boolean isShowAds = true;
    boolean isShowAdsResume = true;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            AppOpenManager.getInstance().isShowingAdsOnResumeBanner = true;
            AppOpenManager.getInstance().isShowingAdsOnResume = true;
        }
    }
}
