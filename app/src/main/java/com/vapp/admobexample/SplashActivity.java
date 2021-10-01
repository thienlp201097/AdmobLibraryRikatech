package com.vapp.admobexample;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vapp.admobexample.view.MainActivity;
import com.vapp.admobexample.view.OtherActivity;
import com.vapp.admoblibrary.utils.Utils;
import com.vapp.admoblibrary.ads.AdCallback;
import com.vapp.admoblibrary.ads.AdmodUtils;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        AdmodUtils.getInstance().loadAndShowAdInterstitialWithCallback(this, getString(R.string.test_ads_admob_inter_id), 0, new AdCallback() {
            @Override
            public void onAdClosed() {
                Utils.getInstance().replaceActivity(SplashActivity.this, MainActivity.class);
            }

            @Override
            public void onAdFail() {
               onAdClosed();
            }
        },false);
    }
}
