package com.nishita.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessTokenTracker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class teacher_login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private AccessTokenTracker accessToken;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        final EditText emailId=findViewById(R.id.username);
        final EditText password=findViewById(R.id.password);
        Button btnsigup=findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();
        ImageView back=findViewById(R.id.back);
        Button loginButton=findViewById(R.id.login);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser mFirebaseuse=mAuth.getCurrentUser();
                if (mFirebaseuse != null ) {
                    Toast.makeText(teacher_login.this, "You are logged in.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(teacher_login.this, Main2Activity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(teacher_login.this, "Please Login.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(teacher_login.this, page2_choose.class);
            }
        });
        btnsigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailId.getText().toString();
                String pass=password.getText().toString();
                if(email.isEmpty())
                {emailId.setError("Please enter email Id");
                    emailId.requestFocus();
                }
                else if(pass.isEmpty())
                {password.setError("Please enter Password");
                    password.requestFocus();
                }
                else if(email.isEmpty() && pass.isEmpty())
                {Toast.makeText(teacher_login.this,"Fields are empty",Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && pass.isEmpty()))
                {
                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(teacher_login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {Toast.makeText(teacher_login.this, "Login Error, Please Login here", Toast.LENGTH_SHORT).show();}
                            else{
                                Intent intr=new Intent(teacher_login.this,Main2Activity.class);
                                startActivity(intr);
                            }
                        }
                    });
                }
                else {Toast.makeText(teacher_login.this,"Error Occured!",Toast.LENGTH_SHORT).show();}

            }
        });



    }
}