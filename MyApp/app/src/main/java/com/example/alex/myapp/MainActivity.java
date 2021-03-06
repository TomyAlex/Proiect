package com.example.alex.myapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.drm.ProcessedData;
import android.graphics.Color;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private Button buttonRegister;
    private EditText editTextMail;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        buttonRegister=(Button)findViewById(R.id.buttonRegister);

        editTextMail=(EditText)findViewById(R.id.editTextEmail);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);

        textViewSignin=(TextView)findViewById(R.id.textViewSignin);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }


    private void registerUser()
    {
        String email = editTextMail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            //email is empty
            Toast.makeText(this,"Please enter your email!",Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            //password is empty
            Toast.makeText(this,"Please enter your password!",Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;

        }

        progressDialog.setMessage("Registering User ...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            //User is successfully registered and logged in
                            Toast.makeText(MainActivity.this,"Registered successfully.",Toast.LENGTH_SHORT).show();
                            //Permision must be here!
                            startActivity(new Intent(getApplicationContext(), Harta.class));
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Could not register, please try again.",Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.cancel();
                    }
                });
    }


    @Override
    public void onClick(View v) {
        if(v == buttonRegister)
        {
            registerUser();
        }
        else if (v == textViewSignin)
        {
            startActivity( new Intent(this, login.class));
        }
    }
}
