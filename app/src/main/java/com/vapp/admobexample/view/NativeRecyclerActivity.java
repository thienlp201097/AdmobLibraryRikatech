package com.vapp.admobexample.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ads.google.admobads.admobnative.GoogleENative;
import com.ads.google.admobads.admobnative.GoogleNativeAdAdapter;
import com.vapp.admobexample.R;
import com.vapp.admobexample.adapter.ClickListener;
import com.vapp.admobexample.adapter.ItemModel;
import com.vapp.admobexample.adapter.MainAdapter;

import java.util.ArrayList;

public class NativeRecyclerActivity extends AppCompatActivity implements ClickListener {
    ArrayList<ItemModel> itemModel = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_activity);
        recyclerView = findViewById(R.id.recyclerViewHome);

        itemModel.add(new ItemModel("Create stylish font"));
        itemModel.add(new ItemModel("Create stylish font"));
        itemModel.add(new ItemModel("Create stylish font"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MainAdapter mainAdapter = new MainAdapter(itemModel, this::onNavigate);
        GoogleNativeAdAdapter googleNativeAdAdapter = new GoogleNativeAdAdapter(
                new GoogleNativeAdAdapter.Param(
                        NativeRecyclerActivity.this,
                        mainAdapter, GoogleENative.UNIFIED_SMALL,
                        2,
                        R.layout.layout_ad, R.id.layout_ad));
        recyclerView.setAdapter(googleNativeAdAdapter);

    }

    @Override
    public void onNavigate(int position) {

    }
}
