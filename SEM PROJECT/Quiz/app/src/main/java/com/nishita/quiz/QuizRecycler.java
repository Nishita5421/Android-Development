package com.nishita.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizRecycler extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_recycler);

        ImageView back = (ImageView)findViewById(R.id.back);
        back.bringToFront();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizRecycler.this, CourseJoined.class));
            }
        });

        Intent intent = getIntent();
        CardView cardView = intent.getParcelableExtra("Course Name");

        String course = cardView.getCourse();
        TextView textView = findViewById(R.id.CourseTitle);
        textView.setText(course);

        Button quizButton = findViewById(R.id.quizButton);
        quizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizRecycler.this, QuizDetails.class));
            }
        });

    }
}