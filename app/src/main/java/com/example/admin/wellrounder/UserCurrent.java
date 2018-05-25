package com.example.admin.wellrounder;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 12/9/2017.
 */

public class UserCurrent {


    public static final String mId = "id";
    public String usernameUP = "username";
    public static final String mEmail = "email";
    public static final String mPhonenumber = "phonenumber";
    public static final String mLastname = "lastname";
    public static final String mFirstname = "firstname";
    public static final String mMiddlename = "middlename";
    public static final String JSON_ARRAY = "results";

    private String id2 ;
    private String username2  = "username";
    private String email2 = "email";
    private String phonenumber2 = "phonenumber";
    private String lastname2 = "lastname";
    private String firstname2 = "firstname";
    private String middlename2 = "middlename";

    private Context context;

    private Integer mIdCurrent;




//    public UserCurrent(String username, Context context) {
//        this.usernameUP = username;
//        this.context = context;
//
//
//
//
//    }



    public String getUsername2() {
        return username2;
    }

    public String getEmail2() {
        return email2;
    }

    public String getPhonenumber2() {
        return phonenumber2;
    }

    public String getLastname2() {
        return lastname2;
    }

    public String getFirstname2() {
        return firstname2;
    }

    public String getMiddlename2() {
        return middlename2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public Integer getId2(){

        int returnThis = Integer.parseInt(id2);

        return returnThis;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public void setPhonenumber2(String phonenumber2) {
        this.phonenumber2 = phonenumber2;
    }

    public void setLastname2(String lastname2) {
        this.lastname2 = lastname2;
    }

    public void setFirstname2(String firstname2) {
        this.firstname2 = firstname2;
    }

    public void setMiddlename2(String middlename2) {
        this.middlename2 = middlename2;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setmIdCurrent(Integer mIdCurrent) {
        this.mIdCurrent = mIdCurrent;
    }

    public Integer getmIdCurrent() {
        return mIdCurrent;
    }
}


