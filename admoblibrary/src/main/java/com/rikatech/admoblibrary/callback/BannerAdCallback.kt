package com.rikatech.admoblibrary.callback;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdValue;

public interface BannerAdCallback {
    void onBannerAdLoaded(AdSize adSize);
    void onAdFail();
    void onAdPaid(AdValue adValue);
}
