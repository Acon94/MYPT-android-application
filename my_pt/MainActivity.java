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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //declare componets
    private Button buttonRegister;
    private Button buttonLogin;
    private EditText editTextPassword;
    private EditText editTextEmail;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();


        //check for user
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), UserAct.class));
        }

        //find components by ID


        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        editTextEmail.setBackgroundColor(Color.WHITE);
        editTextPassword.setBackgroundColor(Color.WHITE);

        buttonRegister.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);

    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (editTextEmail.getText().toString().equals("")) {
            Toast.makeText(this, "ensure Email is filled in", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(this ,MainActivity.class));
            return;
        }
        if (password.equals("")) {
            Toast.makeText(this, "ensure password is filled in", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(this ,MainActivity.class));
            return;
        }
        progressDialog.setMessage("Registraion in progress please wait");
        progressDialog.show();

        // use the firebase methods to create a user
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();//dissmiss the dialog
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "REG complete", Toast.LENGTH_SHORT).show();
                    if (firebaseAuth.getCurrentUser() != null) {
                        finish();
                        startActivity(new Intent(getApplicationContext(), UserAct.class));
                    }

                } else {
                    Toast.makeText(MainActivity.this, "REG incomplete", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            registerUser();
        } else if (v == buttonLogin) {

            startActivity(new Intent(this, LoginAct.class));
        }

    }
}
