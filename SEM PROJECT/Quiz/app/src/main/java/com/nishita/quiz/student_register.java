package com.nishita.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.nishita.quiz.R.id.register;

public class student_register extends AppCompatActivity {
    private EditText name,password,emailId,reg;
    FirebaseFirestore fstore;
    FirebaseAuth mAuth;
    private String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
       // textview = findViewById(R.id.textView2);
        mAuth = FirebaseAuth.getInstance();
        reg=findViewById(R.id.name2);
        name=findViewById(R.id.name);
        emailId = findViewById(R.id.email);
        password = findViewById(R.id.pass);
        Button button = findViewById(register);
        fstore=FirebaseFirestore.getInstance();

        ImageView back = (ImageView) findViewById(R.id.back);
        back.bringToFront();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(student_register.this, page2_choose.class));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              final   String email = emailId.getText().toString();
                final String pass = password.getText().toString();
               final String naam= name.getText().toString();
               final String num= reg.getText().toString();
                if (email.isEmpty() || pass.isEmpty()) {
                    emailId.setError("Please enter email Id");
                    emailId.requestFocus();

                } else if (email.equals(" ") && pass.equals(" ")) {
                    Toast.makeText(student_register.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pass.isEmpty())){
                    mAuth.createUserWithEmailAndPassword(email,pass)
                            .addOnCompleteListener(student_register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful())
                        {Toast.makeText(student_register.this,"SignUp Unsuccessful,Please try again",Toast.LENGTH_SHORT).show();
                        }
                        else {

                            userID= Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                            DocumentReference documentReference=fstore.collection("student").document(userID);
                            Map<String,Object> user=new HashMap<>();
                            user.put("Name",naam);
                            user.put("Email",email);
                            user.put("Reg No.",num);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG","onsuccess: user file is created for"+userID);
                                }
                            });
                            startActivity(new Intent(student_register.this, Student_login.class));

                        }
                    }
                });
                }
                else {Toast.makeText(student_register.this,"Error Occured!",Toast.LENGTH_SHORT).show();


                }


            }
        });




    }



    }






