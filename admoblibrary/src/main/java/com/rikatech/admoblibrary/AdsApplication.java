package com.rikatech.admoblibrary;

import android.app.Application;

import com.rikatech.admoblibrary.ads.AdmobUtils;
import com.rikatech.admoblibrary.ads.AppOpenManager;

public abstract class AdsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AdmobUtils.initAdmob(this, 10000,true, true);
        if(enableAdsResume()) {
            AppOpenManager.getInstance().init(this, getOpenAppAdId());
        }

    }

    public abstract boolean enableAdsResume();

    public abstract String getOpenAppAdId();
}
