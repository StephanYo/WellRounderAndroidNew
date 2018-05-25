package com.example.admin.wellrounder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;



public class ProfileActivity extends AppCompatActivity {

    private TextView tvUsername, tvFirstLastname, tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvUsername = (TextView) findViewById(R.id.tvUsernameProfileActivity);
        tvFirstLastname = (TextView) findViewById(R.id.tvFirstLastNameProfileActivity);
        tvEmail = (TextView) findViewById(R.id.tvEmailProfileActivity);

        tvUsername.setText(SharedPrefManager.getInstance(this).getUsername());
        tvFirstLastname.setText(SharedPrefManager.getInstance(this).getUserFirstName());
        tvEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
    }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
        return true;
    }
}
