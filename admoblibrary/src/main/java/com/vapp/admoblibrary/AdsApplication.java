package com.vapp.admoblibrary;

import android.app.Application;

import com.vapp.admoblibrary.ads.AdmobUtils;
import com.vapp.admoblibrary.ads.AppOpenManager;

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
