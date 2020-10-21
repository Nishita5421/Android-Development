package com.nishita.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;



public class Splash extends AppCompatActivity {
    public static List<Modal> catlist=new ArrayList<>();
    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firestore=FirebaseFirestore.getInstance();

        new Thread() {
            public void run() {

                try {
                    sleep(3000);
                 //   loaddata();
                    Intent intent=new Intent(Splash.this,Main2Activity.class);
                    startActivity(intent);
                    Splash.this.finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();


    }
    private void loaddata()
    {
        catlist.clear();
        firestore.collection("Quiz").document("Questions")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        long count = (long) doc.get("Count");
                        for (int i = 1; i <= count; i++) {

                            catlist.add(new Modal("Question"+i,R.drawable.ic_sort_black_24dp));
                        }
                        Intent intent=new Intent(Splash.this,Main2Activity.class);
                        startActivity(intent);
                        Splash.this.finish();
                    }
                else {
                        Toast.makeText(Splash.this,"No Questions exists",Toast.LENGTH_SHORT).show();
                }
                }
                else
                {
                    Toast.makeText(Splash.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }}
                   );
            }


}
