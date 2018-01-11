package com.dupleit.kotlin.kotlinapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mandeep on 3/01/18.
 */

public class PreferenceManager {
    Context context;

    public PreferenceManager(Context context) {
        this.context = context;
    }

    public void saveUserDetails(String email, String name, String image, String mobile, String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("name", name);
        editor.putString("image", image);
        editor.putString("mobile", mobile);
        editor.putString("userid", id);
        editor.apply();
    }

    public void saveUserMobile(Long mobile) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mobile", String.valueOf(mobile));
        editor.apply();
    }
    public void saveUserEmail(String email) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.apply();
    }

    public void saveAuthUID(String AuthUID) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("authUID", AuthUID);
        editor.apply();
    }

    public void userfblogin(boolean id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("loginfb", id);
        editor.apply();
    }

    public Boolean getuserfblogin() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("loginfb",false);
    }


    public void saveUserImage(String image) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("image", image);
        editor.apply();
    }
    public void saveUserName(String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.apply();
    }


    public String getUserEmail() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", "");
    }

    public String getUserMobile() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("mobile", "");
    }

    public String getUserImage() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("image", "");
    }

    public String getUsername() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("name", "");
    }

    public String getUserAddress() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("address", "");
    }

    public String getUserID() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userid", "");
    }



}
