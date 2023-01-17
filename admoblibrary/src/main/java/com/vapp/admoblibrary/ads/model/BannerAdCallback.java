package com.vapp.admoblibrary.ads.model;

import com.google.android.gms.ads.AdSize;

public interface BannerAdCallback {
    void onBannerAdLoaded(AdSize adSize);
    void onAdFail();
}
