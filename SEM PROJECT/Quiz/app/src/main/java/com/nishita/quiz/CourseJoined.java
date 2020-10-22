package com.nishita.quiz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class CourseJoined extends AppCompatActivity {

    String string;
    String st;
    int position;
    public ArrayList<CardView> mCardView;
    public RecyclerView mRecyclerView;
    public Adapter1 mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;
    AlertDialog.Builder alert;
    AlertDialog alertDialog;
    public Button addCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_joined);



        ImageView back = (ImageView) findViewById(R.id.back);
        back.bringToFront();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CourseJoined.this, Main2Activity.class));
            }
        });


        st = getIntent().getExtras().getString("Value");
        mCardView = new ArrayList<>();
        mCardView.add( new CardView(R.drawable.courses_bg, st, "SCHOOL NAME"));
        buildRecyclerView();


        addCourse = findViewById(R.id.fab);
        addCourse.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                View mView = getLayoutInflater().inflate(R.layout.activity_coursecode, null, false);
                final Button join = (Button) mView.findViewById(R.id.join);
                final Button cancel = (Button) mView.findViewById(R.id.cancel);
                final EditText EnterCourseCode = mView.findViewById(R.id.EnterCourseCode);

                alert = new AlertDialog.Builder(CourseJoined.this);
                alert.setView(mView); //To set the entered text
                //To not get canceled if the user touches anywhere else
                join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {





                        string = EnterCourseCode.getText().toString();
                        mCardView.add(new CardView(R.drawable.courses_bg, string, "SCHOOL NAME"));
                        buildRecyclerView();
                        alertDialog.cancel();
                        alertDialog.dismiss();

                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.dismiss();
                    }
                });

                alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();


            }


        });

    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Adapter1(mCardView);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        VerticalSpacing itemDecorator = new VerticalSpacing(10);
        mRecyclerView.addItemDecoration(itemDecorator);
        mAdapter.setOnItemClickListener(new Adapter1.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Intent intent = new Intent(CourseJoined.this, QuizRecycler.class);
                intent.putExtra("Course Name", (Parcelable) mCardView.get(position));
                startActivity(intent);
            }
        });
}}