package com.jambi.macbookpro.smsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Button btn_signin = (Button) findViewById(R.id.button_signin);
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.button_signin){
                    Intent goToMainActivity = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(goToMainActivity);
                }
            }
        });
    }
}
