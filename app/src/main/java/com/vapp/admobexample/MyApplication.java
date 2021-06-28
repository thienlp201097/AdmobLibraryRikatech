package com.vapp.admobexample;

import android.content.Context;

import androidx.multidex.MultiDex;

import com.vapp.admoblibrary.AdsMultiDexApplication;
import com.vapp.admoblibrary.ads.AdmodUtils;
import com.vapp.admoblibrary.ads.AppOpenManager;

public class MyApplication extends AdsMultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AdmodUtils.getInstance().initAdmob(this, true, true,true);
        if (enableAdsResume()) {
            AppOpenManager.getInstance().init(this, getOpenAppAdId());
            AppOpenManager.getInstance().disableAppResumeWithActivity(SplashActivity.class);

        }

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public boolean enableAdsResume() {
        return true;
    }

    @Override
    public String getOpenAppAdId() {
        return "ca-app-pub-3940256099942544/3419835294";
    }


}
