package com.example.admin.wellrounder;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * This class is the register user class. That allows people to create accounts. Still need to do the passwords and email matching thing
 */

public class RegisterUser extends AppCompatActivity {

    private EditText etUsername, etPassword, etEmail, etBirthDate, etFirstName, etLastName, etMiddleName, etPasswordConfirm, etPhoneNumberRA;
    private Button btnCreateUser;
    private ProgressDialog progressDialog;
    private TextView tvAlreadyHaveAccount;

    private TextView tvBirthDayTest;

    private boolean isCorrect = false;

    private String isCorrectString = "";

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private String mBirthdate;
    private Integer mBirthYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, HomePage.class));
            return;
        }

        etUsername = (EditText) findViewById(R.id.etUserRegisterUser);
        etPassword = (EditText) findViewById(R.id.etPasswordRegisterUser);
        etPasswordConfirm = (EditText) findViewById(R.id.etPasswordConfrimRegAcc);
        btnCreateUser = (Button) findViewById(R.id.btnCreateAccount);
        etEmail = (EditText) findViewById(R.id.etEmailRegisterUser);
        etBirthDate = (EditText) findViewById(R.id.etBirthDateReg);
        etFirstName = (EditText) findViewById(R.id.etFirstNameReg);
        etLastName = (EditText) findViewById(R.id.etLastNameReg);
        etMiddleName = (EditText) findViewById(R.id.etMiddleNameReg);
        etPhoneNumberRA = (EditText) findViewById(R.id.etPhoneNumberRA);
        tvAlreadyHaveAccount = (TextView) findViewById(R.id.tvAlreadyHaveAccount);



        progressDialog = new ProgressDialog(this);

        btnCreateUser.setOnClickListener(onClickListener);
        tvAlreadyHaveAccount.setOnClickListener(onClickListener);

        etBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegisterUser.this,
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                mDateSetListener,
                                year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                mBirthdate = month + "/" + day + "/" + year;
                mBirthYear = year;
                etBirthDate.setText(mBirthdate);
            }
        };

    }



    public void correctLogin() {

        String pswrd = etPassword.getText().toString();
        String pswrdConfirm = etPasswordConfirm.getText().toString();


        if (etUsername.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please do not leave Username empty", Toast.LENGTH_SHORT).show();
        } else if (etEmail.getText().toString().isEmpty()) {
            Toast.makeText(this, "Do not leave Email blank", Toast.LENGTH_SHORT).show();

        } else if (etPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please do not leave Password empty", Toast.LENGTH_SHORT).show();
        } else if (etPasswordConfirm.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please do not leave password empty", Toast.LENGTH_LONG).show();
        } else if (etBirthDate.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please do not leave Birth Date blank", Toast.LENGTH_SHORT).show();
        } else if (etFirstName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please do not leave First Name blank", Toast.LENGTH_SHORT).show();
        } else if (etLastName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please do not leave Last Name blank", Toast.LENGTH_SHORT).show();
        } else if (etPhoneNumberRA.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please do not leave Phonenumber empty", Toast.LENGTH_SHORT).show();
        } else if (etPhoneNumberRA.length() != 10) {
            Toast.makeText(this, "Please enter a valid 10 digit US Phone Number", Toast.LENGTH_SHORT).show();
        } else if (!pswrd.equals(pswrdConfirm)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();

        } else if (mBirthdate.toString().isEmpty()) {
            Toast.makeText(this, "Please do not leave Birth Date empty", Toast.LENGTH_SHORT).show();
        } else{

            registerUser();

        }
    }







    public boolean isCorrect(){

        if(etUsername.getText().toString().isEmpty()){
            return false;
        }else if(etPassword.getText().toString().isEmpty()){
            return false;
        }else if(etPasswordConfirm.getText().toString().isEmpty()){
            return false;
        }else if(etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())){
            return false;
        }else if(etEmail.getText().toString().isEmpty()){
            return false;
        }else if(etBirthDate.getText().toString().isEmpty()){
            return false;
        }else if(etFirstName.getText().toString().isEmpty()){
            return false;

        }else if(etLastName.getText().toString().isEmpty()){
            return false;
        }else if(etPhoneNumberRA.getText().toString().isEmpty()){
            return false;
        }else {


            return true;
        }
    }

    public boolean everythingIsCorrect() {
        isCorrect = false;
        if (etUsername.getText().toString().isEmpty()) {
           Toast.makeText(RegisterUser.this, "Please do not leave Username Blank", Toast.LENGTH_LONG).show();
            isCorrect = false;
        }
        else{
            isCorrect = true;
        }
        if(etEmail.getText().toString().isEmpty()){

            Toast.makeText(RegisterUser.this, "Please do not leave Email Blank", Toast.LENGTH_LONG).show();
            isCorrect = false;
        }
        else {
            isCorrect = true;
        }
        if(etPassword.getText().toString().isEmpty()){

            Toast.makeText(RegisterUser.this, "Please do not leave Password Blank", Toast.LENGTH_LONG).show();
            isCorrect = false;
        }
        else {
            isCorrect = true;
        }
        if(etPasswordConfirm.getText().toString().isEmpty()){

            Toast.makeText(RegisterUser.this, "Please do not leave Password Confirm Blank", Toast.LENGTH_LONG).show();
            isCorrect = false;
        }
        else {
            isCorrect = true;
        }
       // String password1 = etPassword.getText().toString();
      //  String password2 = etPasswordConfirm.getText().toString();
       // if(password1.equals(password2)){

         //   Toast.makeText(RegisterUser.this, "Passwords do not match", Toast.LENGTH_LONG).show();
          //  isCorrect = false;
      //  }
       // else {
       //     isCorrect = true;
        //}
        if(etFirstName.getText().toString().isEmpty()){

            Toast.makeText(RegisterUser.this, "Please do not leave First name Blank", Toast.LENGTH_LONG).show();
            isCorrect = false;
        }
        else {
            isCorrect = true;
        }
        if(etLastName.getText().toString().isEmpty()){

            Toast.makeText(RegisterUser.this, "Please do not leave Last name Blank", Toast.LENGTH_LONG).show();
            isCorrect = false;
        }
        else {
            isCorrect = true;
        }
        if(etBirthDate.getText().toString().isEmpty()){
            Toast.makeText(RegisterUser.this, "Please do not leave Your BirthDay Blank", Toast.LENGTH_LONG).show();
            isCorrect = false;
        }else{
            isCorrect = true;
        }


        return isCorrect;
    }

    private void registerUser() {

        final String email = etEmail.getText().toString().trim();
        final String username = etUsername.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();
        final String birthdate = mBirthdate;
        final String firstname = etFirstName.getText().toString().trim();
        final String lastname = etLastName.getText().toString().trim();
        final String middlename = etMiddleName.getText().toString().trim();
        final String phonenumber = etPhoneNumberRA.getText().toString().trim();


        progressDialog.setMessage("Registering User");
        progressDialog.show();
        //  ProgressBar progressBar = new ProgressBar(MainActivity, null,
        //        android.R.attr.progressBarStyleSmall);
        // progressBar.dismis
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                isCorrectString = "";
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    isCorrectString = jsonObject.getString("message");
                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                   //Toast.makeText(getApplicationContext(), "this is the correct user register?", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Required Fields are Missing" + " " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("email", email);
                params.put("phonenumber", phonenumber);
                params.put("birthdate", birthdate);
                params.put("lastname", lastname);
                params.put("firstname", firstname);
                params.put("middlename", middlename);


                return params;

            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.btnCreateAccount: {

                 // if( isCorrect() == true){
                    correctLogin();
                   // Toast.makeText(RegisterUser.this, isCorrectString, Toast.LENGTH_SHORT).show();
                   //     startActivity(new Intent(getApplicationContext(), MainActivity.class));
                  // }

                    break;
                }


                case R.id.tvAlreadyHaveAccount: {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    break;
                }
            }
        }
    };
}
