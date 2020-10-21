package com.nishita.quiz;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main2Activity extends AppCompatActivity {
    public Button play,exit,delete;
    private FirebaseAuth mAuth;
    private String st;

    private GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ImageView back = (ImageView)findViewById(R.id.back);
        back.bringToFront();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main2Activity.this, student_register.class));
            }
        });
    }

    public void join_course (View view){

        final AlertDialog.Builder alert = new AlertDialog.Builder(Main2Activity.this);
        final View mView = getLayoutInflater().inflate(R.layout.activity_coursecode, null);

        final EditText EnterCourseCode = (EditText)mView.findViewById(R.id.EnterCourseCode);
        Button join = (Button)mView.findViewById(R.id.join);
        Button cancel = (Button)mView.findViewById(R.id.cancel);

        alert.setView(mView); //To set the entered text

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);   //To not get canceled if the user touches anywhere else
        alertDialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                st = EnterCourseCode.getText().toString();
                Intent i = new Intent(Main2Activity.this, CourseJoined.class);
                i.putExtra("Value",st);
                alertDialog.cancel();
                startActivity(i);
                finish();


            }
        });






    }


}
