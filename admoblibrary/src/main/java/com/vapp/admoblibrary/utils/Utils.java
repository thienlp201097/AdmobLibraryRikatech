package com.vapp.admoblibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.LocaleList;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vapp.admoblibrary.R;
import com.vapp.admoblibrary.ads.model.AdUnitListModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.TELEPHONY_SERVICE;

public class Utils {

    private static volatile Utils INSTANCE;


    public static synchronized Utils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Utils();
        }
        return INSTANCE;
    }

    public static List<AdUnitListModel> adUnitLists = new ArrayList<>();
    String countryCode = "";

    public boolean checkCountries(Context context, AdUnitListModel adUnitList) {
        countryCode = getCurrentCountry(context);
        boolean isShowAds = false;
        if (adUnitList.getCountries() == null) {
            isShowAds = true;
            return isShowAds;
        }
        List<String> countries = adUnitList.getCountries();
        if (countries.size() > 0) {
            for (String item : countries) {
                if (item.matches("(?i)(" + countryCode + ").*")) {
                    isShowAds = true;
                }
            }
        } else {
            isShowAds = true;
        }
        return isShowAds;
    }

    public String getCurrentCountry(Context context) {
        String countryCode = "";

        TelephonyManager ts = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        countryCode = ts.getNetworkCountryIso().toUpperCase();

        if (countryCode.length() < 2) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                countryCode = LocaleList.getDefault().get(0).getCountry();
                return countryCode;
            } else {
                countryCode = Locale.getDefault().getCountry();
                return countryCode;
            }
        }
        return countryCode;
    }


    public void showMessenger(Context context, String content, int time) {
        if (time == 0) {
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show();

        }
    }

    public void showMessenger(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }


    public void addActivity(Context context, Class activity) {
        Intent i = new Intent(context, activity);
        context.startActivity(i);
    }

    public void replaceActivity(Context context, Class activity) {
        Intent i = new Intent(context, activity);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public void addFragment(AppCompatActivity context, Fragment fragment, int contentFrame, boolean addToBackStack) {
        FragmentTransaction transaction = context.getSupportFragmentManager()
                .beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        } else {
            transaction.addToBackStack(fragment.toString());
        }
        if (fragment.getTag() == null) {
            transaction.replace(contentFrame, fragment, fragment.toString());
        } else {
            transaction.replace(contentFrame, fragment, fragment.getTag());
        }
        transaction.commit();
    }

    public void replaceFragment(FragmentManager fm, Fragment fragment, int contentFrame, boolean addToBackStack) {
        FragmentTransaction transaction = fm.beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        } else {
            transaction.addToBackStack(fragment.toString());
        }
        if (fragment.getTag() == null) {
            transaction.replace(contentFrame, fragment, fragment.toString());
        } else {
            transaction.replace(contentFrame, fragment, fragment.getTag());
        }
        transaction.commit();
    }

    public AdUnitListModel getAdUnit(String id) {
        if (adUnitLists.size() > 0) {
            for (AdUnitListModel adUnitList1 : adUnitLists) {
                if (adUnitList1.getAdUnitName().equals(id)) {
                    return adUnitList1;
                }
            }
        }
        return null;
    }

    public AdUnitListModel getDefaultAdUnit(String id) {
        AdUnitListModel adUnitList = new AdUnitListModel();
        adUnitList.setIsShow(true);
        adUnitList.setIsAdmob(true);
        adUnitList.setAdmobId(id);
        return adUnitList;
    }

    public AdUnitListModel getAdUnitByName(String name, String defaulID) {
        if (getAdUnit(name) != null) {
            return getAdUnit(name);
        } else {
            return getDefaultAdUnit(defaulID);
        }
    }


}
