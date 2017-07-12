package com.example.alex.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Alex on 7/12/2017.
 */

public class login extends AppCompatActivity implements View.OnClickListener{

    private Button bLogin;
    private TextView tvRegisterLink;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        tvRegisterLink = (TextView)findViewById(R.id.tvRegisterLink);
        tvRegisterLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == tvRegisterLink)
        {
            setContentView(R.layout.activity_main);
            Intent i = new Intent(this,MainActivity.class);
        }
    }
}
