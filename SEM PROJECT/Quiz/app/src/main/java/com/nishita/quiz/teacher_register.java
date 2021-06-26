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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

@SuppressWarnings("ConstantConditions")
public class teacher_register extends AppCompatActivity {
    private EditText name,password,emailId,reg;
    FirebaseFirestore fstore;
    private Button button;
    FirebaseAuth mAuth;
    private String userID;
    private RadioGroup grp;
    private RadioButton radio;
    private TextView textview;
    private int selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_register);

        mAuth = FirebaseAuth.getInstance();
        reg=findViewById(R.id.user);
        name=findViewById(R.id.name);
        emailId = findViewById(R.id.email);
        password = findViewById(R.id.pass);

        fstore= FirebaseFirestore.getInstance();
      Button button=findViewById(R.id.register);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(v -> {
            startActivity(new Intent(teacher_register.this, page2_choose.class));
        });
        button.setOnClickListener(v -> {
            final String email = emailId.getText().toString();
            final String pass = password.getText().toString();
            final String naam= name.getText().toString();
            final String num= reg.getText().toString();
            if (email.isEmpty()) {
                emailId.setError("Please enter email Id");
                emailId.requestFocus();
            } else if (pass.isEmpty()) {
                password.setError("Please enter Password");
                password.requestFocus();
            } else if (email.isEmpty() && pass.isEmpty()) {
                Toast.makeText(teacher_register.this, "Fields are empty", Toast.LENGTH_SHORT).show();
            } else if (!(email.isEmpty() && pass.isEmpty())){mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(teacher_register.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful())
                    {Toast.makeText(teacher_register.this,"SignUp Unsuccessful,Please try again",Toast.LENGTH_SHORT).show();
                    }
                    else {

                        userID=mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference=fstore.collection("teachers").document(email);
                        Map<String,Object> user=new HashMap<>();
                        user.put("Name",naam);
                        user.put("Email",email);
                        user.put("Mob No.",num);

                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG","onsuccess: user file is created for"+userID);
                            }
                        });
                        startActivity(new Intent(teacher_register.this, teacher_login.class));

                    }
                }
            });
            }
            else {Toast.makeText(teacher_register.this,"Error Occured!",Toast.LENGTH_SHORT).show();


            }


        });

    }
}