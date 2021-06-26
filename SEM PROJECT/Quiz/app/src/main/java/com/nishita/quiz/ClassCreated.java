package com.nishita.quiz;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ClassCreated extends AppCompatActivity {

    String st1,st2,string,string1;
    int position1;
    public ArrayList<ClassCard> mClassCard;
    public RecyclerView mRecyclerView1;
    public Adapter2 mAdapter1;
    public RecyclerView.LayoutManager mLayoutManager1;
    private FirebaseFirestore firestore;
    public Button addClass;
    AlertDialog.Builder alert;
    AlertDialog alertDialog;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_created);
        firestore=FirebaseFirestore.getInstance();
        ImageView back = (ImageView)findViewById(R.id.back);
        back.bringToFront();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ClassCreated.this, CreateClass.class));
            }
        });
        mClassCard = new ArrayList<>();
        buildRecyclerView();
        loadData();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            st1 = extras.getString("Code1");
            st2=extras.getString("Code2");
            mClassCard.add(new ClassCard(R.drawable.courses_bg, st1,st2));
            buildRecyclerView();
            //buildRecyclerView();The key argument here must match that used in the other activity
        }







        addClass = findViewById(R.id.addClass);
        addClass.setOnClickListener(view -> {
            View mView = getLayoutInflater().inflate(R.layout.activity_class_code, null, false);
            final Button join = (Button) mView.findViewById(R.id.create);
            final Button cancel = (Button) mView.findViewById(R.id.cancel);
            final EditText EnterClassName = mView.findViewById(R.id.EnterClassName);
            final EditText EnterAccessCode = mView.findViewById(R.id.EnterAccessCode);

            alert = new AlertDialog.Builder(ClassCreated.this);
            alert.setView(mView); //To set the entered text
            //To not get canceled if the user touches anywhere else
            join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {





                    string = EnterClassName.getText().toString();
                    string1= EnterAccessCode.getText().toString();
                    mClassCard.add(new ClassCard(R.drawable.courses_bg, string, string1));
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

            //mAdapter1.notifyDataSetChanged();
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
                            id=doc.getString("CAT"+String.valueOf(i)+"_ID");
                            String catName=doc.getString("CAT"+String.valueOf(i));
                            mClassCard.add(new ClassCard(R.drawable.courses_bg, catName, id));
                            mAdapter1.notifyDataSetChanged();
                            buildRecyclerView();

                        }
                    }
                }
                else
                {
                    Toast.makeText(ClassCreated.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void buildRecyclerView() {
        mRecyclerView1 = findViewById(R.id.recyclerView1);
        mRecyclerView1.setHasFixedSize(true);
        mLayoutManager1 = new LinearLayoutManager(this);
        mAdapter1 = new Adapter2(mClassCard);
        mRecyclerView1.setLayoutManager(mLayoutManager1);
        mRecyclerView1.setAdapter(mAdapter1);
        VerticalSpacing itemDecorator = new VerticalSpacing(10);
        mRecyclerView1.addItemDecoration(itemDecorator);
        mAdapter1.setOnItemClickListener(position -> {


            Intent intent = new Intent(ClassCreated.this, teacher_sets.class);
            intent.putExtra("Course Name2", (Parcelable) mClassCard.get(position));
            intent.putExtra("Course No",position);
            intent.putExtra("click",mClassCard.get(position).getCode());

            startActivity(intent);
        });
    }
}