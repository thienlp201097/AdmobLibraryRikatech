package com.vapp.admobexample.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vapp.admobexample.R;
import com.vapp.admoblibrary.ads.AdmodUtils;

public class OtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        if (AdmodUtils.getInstance().dialog != null) {
            if (AdmodUtils.getInstance().dialog.isShowing()) {
                AdmodUtils.getInstance().dialog.dismiss();
            }
        }


    }
}
