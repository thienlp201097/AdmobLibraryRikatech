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

        itemModel.add(new ItemModel("Create stylish font 1"));
        itemModel.add(new ItemModel("Create stylish font 2"));
        itemModel.add(new ItemModel("Create stylish font 3"));
        itemModel.add(new ItemModel("Create stylish font 4"));
        itemModel.add(new ItemModel("Create stylish font 5"));
        itemModel.add(new ItemModel("Create stylish font 6"));
        itemModel.add(new ItemModel("Create stylish font 7"));
        itemModel.add(new ItemModel("Create stylish font 8"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MainAdapter mainAdapter = new MainAdapter(itemModel, this::onNavigate);
        GoogleNativeAdAdapter googleNativeAdAdapter = new GoogleNativeAdAdapter(
                new GoogleNativeAdAdapter.Param(
                        NativeRecyclerActivity.this,
                        mainAdapter,
                        getString(R.string.test_ads_admob_native_id),
                        R.layout.ad_template_medium, //
                        2,
                        R.layout.layout_ad,
                        R.id.id_ad
                ));
        recyclerView.setAdapter(googleNativeAdAdapter);

    }

    @Override
    public void onNavigate(int position) {

    }
}
