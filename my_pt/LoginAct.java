package com.example.andrew.my_pt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginAct extends AppCompatActivity implements View.OnClickListener {

    //variables
    private Button buttonLogin;

    private EditText editTextPassword;
    private EditText editTextEmail;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();//gets a instace of the firebase

        if (firebaseAuth.getCurrentUser() != null) {//if user exists login
            finish();
            startActivity(new Intent(getApplicationContext(), UserAct.class));
        } else if (firebaseAuth.getCurrentUser() == null) {
            Toast.makeText(this, "ensure you are registered", Toast.LENGTH_SHORT).show();
        }

        buttonLogin = (Button) findViewById(R.id.buttonLogin);


        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail.setBackgroundColor(Color.WHITE);
        editTextPassword.setBackgroundColor(Color.WHITE);
        buttonLogin.setOnClickListener(this);

    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //error checking
        if (editTextEmail.getText().toString().equals("")) {
            Toast.makeText(this, "ensure Email is filled in", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(this ,MainActivity.class));
            return;
        }
        if (password.equals("")) {
            Toast.makeText(this, "ensure password is filled in", Toast.LENGTH_SHORT).show();

            return;
        }
        progressDialog.setMessage("Logging in ");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if (task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), UserAct.class));
                }
            }
        });


    }


    @Override
    public void onClick(View v) {
        if (v == buttonLogin) {
            userLogin();
        }
    }
}
