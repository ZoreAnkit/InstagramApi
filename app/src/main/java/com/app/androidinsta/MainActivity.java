package com.app.androidinsta;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.androidinsta.dialog.AuthenticationDialog;
import com.app.androidinsta.interfaces.AuthenticationListener;



public class MainActivity extends AppCompatActivity implements AuthenticationListener {

    private AuthenticationDialog auth_dialog;

    Button btn_login = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth_dialog = new AuthenticationDialog(MainActivity.this,MainActivity.this);
                auth_dialog.setCancelable(true);
                auth_dialog.show();
            }
        });


    }


    @Override
    public void onCodeReceived(String auth_token) {
        if (auth_token==null)
             {
                auth_dialog.dismiss();
            }

        Intent i = new Intent(MainActivity.this, FeedActivity.class);
        i.putExtra("access_token", auth_token);
        startActivity(i);
    }


}
