package com.example.admin.wellrounder;

/**
 * Created by admin on 9/15/2017.
 *  Class is used by the login page. You can also get the current users email and username from this class.
 *
 */

import android.content.Context;
import android.content.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;



public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private ImageLoader mImageLoader;
    private static Context mCtx;
    private static final String SHARED_PREFRENCE_NAME = "mysharedpref12";
    private static final String KEY_USER_EMAIL = "useremail";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_ID = "id";
    private static final String KEY_FIRST_NAME = "userfirstname";
    private static final String KEY_LAST_NAME = "userlastname";
    private String currentUserId = "";

    private int mCurrentId= 0;
    private SharedPrefManager(Context context) {
        mCtx = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(int id, String username, String email) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFRENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_USER_ID, id);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USERNAME, username);

        mCurrentId = id;
        editor.apply();
        return true;
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFRENCE_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USERNAME, null) != null) {
            return true;
        }
        return false;

    }

    public boolean logOut(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFRENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getUsername(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFRENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);

    }

    public Integer getKeyUserId(){
        //SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFRENCE_NAME, Context.MODE_PRIVATE);
        //String userId = sharedPreferences.getInt(KEY_USER_ID, null);
        return mCurrentId;
    }


    public String getUserEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFRENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }

    public String getUserFirstName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFRENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_FIRST_NAME, null);
    }

    public String getUserLastName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFRENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LAST_NAME, null);
    }

    public String getCurrtUserIDString() {
        return currentUserId;
    }

    public void setCurrtUserID(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public Integer getCurretUserIDint(){

        int mCurrentIdint = Integer.parseInt(currentUserId);
        return mCurrentIdint;
    }

    public int getmCurrentId() {
        return mCurrentId;
    }

    public void setmCurrentId(int mCurrentId) {
        this.mCurrentId = mCurrentId;
    }
}
