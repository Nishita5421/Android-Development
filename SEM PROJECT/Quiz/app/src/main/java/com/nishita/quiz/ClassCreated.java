package com.nishita.quiz;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class ClassCreated extends AppCompatActivity {

    String st1;

    public ArrayList<CardView> mClassCard;
    public RecyclerView mRecyclerView1;
    public RecyclerView.Adapter mAdapter1;
    public RecyclerView.LayoutManager mLayoutManager1;

    public Button addClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_created);

        ImageView back = (ImageView)findViewById(R.id.back);
        back.bringToFront();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ClassCreated.this, CreateClass.class));
            }
        });

        createExampleList();
        buildRecyclerView();

        addClass = findViewById(R.id.addClass);
        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_class();
            }
        });
    }

    void add_class() {
        mClassCard.add(new CardView(R.drawable.courses_bg, "CLASS/COURSE NAME","ACCESS CODE"));
        mAdapter1.notifyDataSetChanged();
    }

    public void createExampleList() {
        mClassCard = new ArrayList<>();
        st1 = getIntent().getExtras().getString("Code");
        mClassCard.add(new CardView(R.drawable.courses_bg, st1,"SCHOOL NAME"));
    }

    public void buildRecyclerView() {
        mRecyclerView1 = findViewById(R.id.recyclerview);
        mRecyclerView1.setHasFixedSize(true);
        mLayoutManager1 = new LinearLayoutManager(this);
        mAdapter1 = new Adapter1(mClassCard);
        mRecyclerView1.setLayoutManager(mLayoutManager1);
        mRecyclerView1.setAdapter(mAdapter1);
        VerticalSpacing itemDecorator = new VerticalSpacing(10);
        mRecyclerView1.addItemDecoration(itemDecorator);
    }
}