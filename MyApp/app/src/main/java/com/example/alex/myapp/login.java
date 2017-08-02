package com.example.alex.myapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Alex on 7/12/2017.
 */

public class login extends AppCompatActivity implements View.OnClickListener{

    private Button loginButton;
    private TextView tvRegisterLink;
    private EditText password;
    private EditText email;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            //profile activity here
            Intent i = new Intent(getApplicationContext(), Harta.class);
        }



        tvRegisterLink = (TextView)findViewById(R.id.tvRegisterLink);
        tvRegisterLink.setOnClickListener(this);

        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);


        password = (EditText)findViewById(R.id.password);
        email = (EditText)findViewById(R.id.email);

        progressDialog = new ProgressDialog(this);

    }

    private void userLogin(){
        String emailStr = email.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();

        if(TextUtils.isEmpty(emailStr))
        {
            //email is empty
            Toast.makeText(this,"Please enter your email!",Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }
        if(TextUtils.isEmpty(passwordStr))
        {
            //password is empty
            Toast.makeText(this,"Please enter your password!",Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;

        }

        progressDialog.setMessage("Loading in ...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(emailStr,passwordStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            //Start the map activity
                            //Permision must be here!
                            startActivity(new Intent(getApplicationContext(), Harta.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v == tvRegisterLink){
            startActivity(new Intent(this,MainActivity.class));
        }else if (v == loginButton){
            userLogin();
        }
    }
}
