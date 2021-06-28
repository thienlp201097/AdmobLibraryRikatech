package com.vapp.admobexample.iap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vapp.admobexample.R;
import com.vapp.admoblibrary.ads.AdmodUtils;
import com.vapp.admoblibrary.ads.AppOpenManager;
import com.vapp.admoblibrary.iap.PurchaseUtils;

public class IAPActivity extends AppCompatActivity {
    private TextView tvStatus;
    private Button btnPremium;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iapp);

        tvStatus = findViewById(R.id.tv_status);
        btnPremium = findViewById(R.id.btn_premium);

        PurchaseUtils.getInstance().initBilling(this,getString(R.string.play_console_license));

        btnPremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseUtils.getInstance().subscribe(IAPActivity.this,getString(R.string.premium));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        new Handler().postDelayed(() -> {
            if (PurchaseUtils.getInstance().isPurchased(getString(R.string.premium))) {
                tvStatus.setText("Vip");
            }else {
                tvStatus.setText("Free");
            }
        }, 1000);

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(() -> {
            if (PurchaseUtils.getInstance().isPurchased(getString(R.string.premium))) {
                tvStatus.setText("Vip");
            }else {
                tvStatus.setText("Free");
            }
        }, 800);
    }
}
