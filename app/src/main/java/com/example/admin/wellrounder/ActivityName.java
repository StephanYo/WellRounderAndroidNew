package com.example.admin.wellrounder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by admin on 3/2/2018.
 */


public class ActivityName extends FragmentActivity {

    private Menu menu;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            HomePage homePage = new HomePage();
      //  homePage.onNavigationItemSelected(R.menu.activity_home_page_drawer);

      //  menu = findViewById(R.menu.activity_home_page_drawer);
       // MenuItem menuItem =  menu.findItem(R.id.nav_homePage);
        //homePage.onNavigationItemSelected(menuItem);


        //if (savedInstanceState == null){
          //  getSupportFragmentManager().beginTransaction()
            //        .add(android.R.id.content, new NavHomePage()).commit();}
    }
}
