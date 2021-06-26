package com.nishita.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class teacher_sets extends AppCompatActivity {
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    private FirebaseFirestore firestore;
    private int quiz_set;
    private Button add_set,delete_set;
    String id;
    String Counter, no_of_sets;

   @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_teacher_sets);
            listView = findViewById(R.id.listview);
            ImageView back = (ImageView) findViewById(R.id.back);
            back.bringToFront();
            firestore = FirebaseFirestore.getInstance();
            add_set=findViewById(R.id.fab);
            quiz_set = getIntent().getIntExtra("Course No", 1);
             id = getIntent().getStringExtra("click");

            arrayList = new ArrayList<>();
            adapter = new ArrayAdapter<String>(this, R.layout.row2, R.id.quizset, arrayList);
       delete_set=findViewById(R.id.delete);
            listView.setAdapter(adapter);
       Intent intent = getIntent();
       ClassCard classCard = intent.getParcelableExtra("Course Name2");

       String course = classCard.getCourse1();

       TextView textView = findViewById(R.id.CourseTitle);
       textView.setText(course);
       Toast.makeText(teacher_sets.this,id,Toast.LENGTH_SHORT).show();

       Toast.makeText(teacher_sets.this, id, Toast.LENGTH_SHORT).show();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(teacher_sets.this,String.valueOf(position),Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(teacher_sets.this, QuizDetails.class));
                }
            });
            add_set.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    add_newset();
                    //startActivity(new Intent(teacher_sets.this, CreateQuiz.class));
                }
            });
listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final int item = position;
        final View mView = getLayoutInflater().inflate(R.layout.delete_dialog, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(teacher_sets.this);
        alert.setView(mView); //To set the entered text
        final AlertDialog alertDialog = alert.create();
        Button delete = (Button) mView.findViewById(R.id.delete);
        Button cancel = (Button) mView.findViewById(R.id.cancel);

        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
           delete_set(item);

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


        alertDialog.show();
        return true;
    }
});
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(teacher_sets.this, ClassCreated.class));
                }
            });



            loadData();

        }

    private void delete_set(int item) {
           }

    private void add_newset() {
        Map<String, Object> qData = new ArrayMap<>();
        qData.put("COUNT","0");

        firestore.collection("Quiz").document(id).collection(Counter).document("QUESTION_LIST")
                .set(qData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Map<String, Object> catDoc = new ArrayMap<>();
                catDoc.put("COUNTER", String.valueOf(Integer.valueOf(Counter) + 1));
                catDoc.put("SET" + String.valueOf(arrayList.size() + 1) + "_ID", Counter);
                catDoc.put("SETS", arrayList.size() + 1);
                firestore.collection("Quiz").document(id).update(catDoc)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(teacher_sets.this, "QUIZ set added", Toast.LENGTH_LONG);
                                arrayList.add("QUIZ:" + String.valueOf(Counter));
                                Counter= String.valueOf(Integer.valueOf(Counter)+1);
                                adapter.notifyDataSetChanged();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(teacher_sets.this, "ERROR try again!!!", Toast.LENGTH_LONG);
                    }
                });
            }})
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(teacher_sets.this, "ERROR", Toast.LENGTH_LONG);
                    }
                });
    }

    void loadData()
{firestore.collection("Quiz").document(id)
        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                long sets = (long) documentSnapshot.get("SETS");


                for (int i = 1; i <= sets; i++) {

                    Toast.makeText(teacher_sets.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                    arrayList.add("QUIZ:" + String.valueOf(i));
                    adapter.notifyDataSetChanged();


                }
                Counter=documentSnapshot.getString("COUNTER");
                no_of_sets=documentSnapshot.getString(String.valueOf(sets));
            }})

.addOnFailureListener(new OnFailureListener() {
    @Override
    public void onFailure(@NonNull Exception e) {

        Toast.makeText(teacher_sets.this, e.getMessage(), Toast.LENGTH_SHORT).show();

    }
});


}


    }

