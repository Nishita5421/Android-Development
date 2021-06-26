package com.nishita.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;

public class QuizRecycler extends AppCompatActivity {
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    private FirebaseFirestore firestore;
    private int quiz_set;
    String id,catid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_recycler);
        listView = findViewById(R.id.listview);
        ImageView back = (ImageView) findViewById(R.id.back);
        back.bringToFront();
        firestore = FirebaseFirestore.getInstance();

        quiz_set = getIntent().getIntExtra("Course No", 1);
        catid = getIntent().getStringExtra("click");
        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.row, R.id.quizset, arrayList);
        listView.setAdapter(adapter);
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Toast.makeText(QuizRecycler.this,String.valueOf(position),Toast.LENGTH_SHORT).show();
               startActivity(new Intent(QuizRecycler.this, QuizDetails.class));
           }
       });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizRecycler.this, CourseJoined.class));
            }
        });

        Intent intent = getIntent();
        CardView cardView = intent.getParcelableExtra("Course Name");

        String course = cardView.getCourse();
        id=cardView.getSchool();
        TextView textView = findViewById(R.id.CourseTitle);
        textView.setText(course);

        Toast.makeText(QuizRecycler.this, id, Toast.LENGTH_SHORT).show();



        firestore.collection("Quiz").document(catid)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        long sets = (long) doc.get("SETS");


                        for (int i = 1; i <= sets; i++) {


                            arrayList.add("QUIZ:" + String.valueOf(i));
                            adapter.notifyDataSetChanged();


                        }

                    }
                } else {
                    Toast.makeText(QuizRecycler.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}