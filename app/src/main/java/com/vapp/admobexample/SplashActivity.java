package com.vapp.admobexample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.vapp.admobexample.utilsdemp.AdsManager;
import com.vapp.admobexample.utilsdemp.RemoteConfigManager;
import com.vapp.admobexample.view.MainActivity;
import com.vapp.admobexample.view.OtherActivity;
import com.vapp.admoblibrary.ads.AOAManager;
import com.vapp.admoblibrary.ads.AdCallbackNew;
import com.vapp.admoblibrary.ads.AdLoadCallback;
import com.vapp.admoblibrary.ads.AppOpenManager;
import com.vapp.admoblibrary.utils.Utils;
import com.vapp.admoblibrary.ads.AdCallback;
import com.vapp.admoblibrary.ads.AdmodUtils;

public class SplashActivity extends AppCompatActivity {
    AOAManager aoaManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        if ((!isTaskRoot() && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)) && getIntent().getAction() != null && getIntent().getAction() == Intent.ACTION_MAIN) {
            finish();
            return;
        }
        RemoteConfigManager.INSTANCE.initRemoteConfig(task -> {

        });
        aoaManager = new AOAManager(this, AdsManager.INSTANCE.getAoaHolder(), 20000, new AOAManager.AppOpenAdsListener() {
            @Override
            public void onAdsClose() {
                Utils.getInstance().replaceActivity(SplashActivity.this, MainActivity.class);
            }

            @Override
            public void onAdsFailed() {
                Utils.getInstance().replaceActivity(SplashActivity.this, MainActivity.class);
            }
        });
        aoaManager.loadAndShowAoA();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aoaManager.onAoaDestroyed();
    }
}


