package com.nishita.quiz;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main2Activity extends AppCompatActivity {
    public Button play,exit,delete;
    private FirebaseAuth mAuth;

    private GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        play = findViewById(R.id.button2);
        exit=findViewById(R.id.button3);
        delete=findViewById(R.id.button4);
        mAuth=FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent news = new Intent(Main2Activity.this,categoryActivity.class);
                startActivity(news);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOut();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                signOut();
                finish();
            }
        });
    }
    private void signOut() {
        mGoogleSignInClient.signOut();
        mAuth.signOut();
        Toast.makeText(Main2Activity.this,"Logout successfully",Toast.LENGTH_SHORT).show();

    }
    private void deleteOut() {
        mGoogleSignInClient.signOut();
      FirebaseUser firebaseuser=mAuth.getCurrentUser();
      firebaseuser.delete();
        Toast.makeText(Main2Activity.this,"Your account is deleted",Toast.LENGTH_SHORT).show();

    }

}
