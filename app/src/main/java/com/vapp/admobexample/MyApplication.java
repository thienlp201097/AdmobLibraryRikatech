package com.vapp.admobexample;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.vapp.admoblibrary.ads.AdmodUtils;
import com.vapp.admoblibrary.ads.AppOpenManager;

public class MyApplication extends Application {
    boolean isShowAds = true;
    boolean isShowAdsResume = true;

    @Override
    public void onCreate() {
        super.onCreate();
        AdmodUtils.initAdmob(this, 10000, true, isShowAds);
        if (isShowAdsResume) {
            AppOpenManager.getInstance().init(this, getString(R.string.test_ads_admob_app_open));
            AppOpenManager.getInstance().disableAppResumeWithActivity(SplashActivity.class);
        }
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
