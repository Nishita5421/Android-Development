package com.nishita.quiz;


import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class CourseJoined extends AppCompatActivity {

    String string;
    private FirebaseFirestore firestore;
    String st;
    int position;
    public ArrayList<CardView> mCardView;
    public RecyclerView mRecyclerView;
    public Adapter1 mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;
    AlertDialog.Builder alert;
    AlertDialog alertDialog;
    public Button addCourse;
    String catid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_joined);


    firestore=FirebaseFirestore.getInstance();

        ImageView back = (ImageView) findViewById(R.id.back);
        back.bringToFront();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CourseJoined.this, Main2Activity.class));
            }
        });
        mCardView = new ArrayList<>();
        buildRecyclerView();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("Value");
            mCardView.add( new CardView(R.drawable.courses_bg, value, "SCHOOL NAME"));
            buildRecyclerView();
            //buildRecyclerView();The key argument here must match that used in the other activity
        }
        loadData();


        addCourse = findViewById(R.id.fab);
        addCourse.setOnClickListener((View.OnClickListener) view -> {
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


        });

    }
private void loadData()
{
    firestore.collection("Quiz").document("Course")
            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if(task.isSuccessful())
            {
                DocumentSnapshot doc = task.getResult();
                if(doc.exists())
                {
                    long count=(long)doc.get("COUNT");
                    for(int i=1; i<=count;i++)
                    {
                        catid = doc.getString("CAT" + String.valueOf(i)+"_ID");
                            String catName = doc.getString("CAT" + String.valueOf(i));
                            mCardView.add(new CardView(R.drawable.courses_bg, catName, catid));
                            mAdapter.notifyDataSetChanged();
                            buildRecyclerView();

                    }
                }
            }
            else
            {
                Toast.makeText(CourseJoined.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
            }
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
        mAdapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(CourseJoined.this, QuizRecycler.class);
            intent.putExtra("Course Name", mCardView.get(position));
            intent.putExtra("Course No",position);
            intent.putExtra("click",mCardView.get(position).getSchool());
            Toast.makeText(CourseJoined.this,mCardView.get(position).getSchool(),Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });
}}