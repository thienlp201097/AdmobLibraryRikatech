package com.vapp.admoblibrary;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vapp.admoblibrary.iap.PurchaseUtils;

public class Utils {


//    showDialog()
//    dismissDialog(theme)


    private static volatile Utils INSTANCE;


    public static synchronized Utils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Utils();
        }
        return INSTANCE;
    }

   public void showMessenger(Context context, String content, int time){
        if (time == 0){
            Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,content,Toast.LENGTH_SHORT).show();

        }
   }

    public void showMessenger(Context context, String content){
            Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }


    public  void addActivity(Context context, Class activity){
        Intent i =  new Intent(context,activity);
        context.startActivity(i);
    }

    public  void replaceActivity(Context context, Class activity){
        Intent i =  new Intent(context,activity);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public  void addFragment(AppCompatActivity context, Fragment fragment, int contentFrame, boolean addToBackStack) {
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

    public  void replaceFragment(FragmentManager fm, Fragment fragment, int contentFrame, boolean addToBackStack) {
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
}
