package com.vapp.admobexample;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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
        aoaManager = new AOAManager(this, "", 10000, new AOAManager.AppOpenAdsListener() {
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


