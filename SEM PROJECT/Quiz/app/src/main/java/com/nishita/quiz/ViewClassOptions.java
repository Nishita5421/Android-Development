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

public class ViewClassOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_class_options);

        ImageView back = (ImageView)findViewById(R.id.back);
        back.bringToFront();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewClassOptions.this, ClassCreated.class));
            }
        });

        Intent intent = getIntent();
        ClassCard classCard = intent.getParcelableExtra("Course Name2");

        String course = classCard.getCourse1();
        TextView textView = findViewById(R.id.classname2);
        textView.setText(course);

        Button quizButton = findViewById(R.id.createquiz);
        quizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewClassOptions.this, CreateQuiz.class));
            }
        });
        Button viewquiz = findViewById(R.id.viewquiz);
        viewquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewClassOptions.this, Viewquiz.class));
            }
        });

    }
}