package com.vapp.admobexample;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vapp.admoblibrary.Utils;
import com.vapp.admoblibrary.ads.AdCallback;
import com.vapp.admoblibrary.ads.AdmodUtils;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        AdmodUtils.getInstance().loadAndShowAdInterstitialWithCallback(this, getString(R.string.ads_admob_inter_id), 0, new AdCallback() {
            @Override
            public void onAdClosed() {
                Utils.getInstance().replaceActivity(SplashActivity.this,MainActivity.class);
            }

            @Override
            public void onAdFail() {
                Utils.getInstance().replaceActivity(SplashActivity.this,MainActivity.class);

            }
        },false);
    }
}
