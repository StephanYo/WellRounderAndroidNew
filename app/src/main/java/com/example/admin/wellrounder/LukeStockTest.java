package com.example.admin.wellrounder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LukeStockTest extends AppCompatActivity {

    Button click;
    public static TextView data;
    public static TextView dataParsed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luke_stock_test);

        click = (Button) findViewById(R.id.buttonLuke);
        data = (TextView) findViewById(R.id.fetcheddata);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData process = new fetchData();
                process.execute();
                Log.d("LukeStockTest", "It has begun executing");

            }
        });
    }
}
