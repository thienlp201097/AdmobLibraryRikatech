package com.vapp.admoblibrary.ads;

public interface RewardAdCallback {
    void onAdClosed();
    void onAdFail(String message);
    void onEarned();

}
