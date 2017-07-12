package com.example.alex.myapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.google.firebase.FirebaseApp;

/**
 * Created by Alex on 7/12/2017.
 */

public class UserAddThings extends AppCompatActivity {

    private Button btAddThings;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_add_things);

        btAddThings = (Button)findViewById(R.id.btAddDatabase);
    }
}
