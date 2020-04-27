package com.nishita.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Questions extends AppCompatActivity implements View.OnClickListener {
    private TextView question,num,timer;
    private Button optionA,optionB,optionC,optionD;
    private Button back,next;
    private List<Questionmodal> list;
    static int quesnum;
    private CountDownTimer count;
    private int score;
    private FirebaseFirestore firestorer;
    private int catno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

         catno=getIntent().getIntExtra("Questions",1);
        question=findViewById(R.id.ques);
        back=findViewById(R.id.button9);
        next=findViewById(R.id.button10);
        num=findViewById(R.id.num);
        optionA=findViewById(R.id.button5);
        optionB=findViewById(R.id.button6);
        optionC=findViewById(R.id.button7);
        optionD=findViewById(R.id.button8);
        timer=findViewById(R.id.timer);
        optionA.setOnClickListener(this);
        optionB.setOnClickListener(this);
        optionC.setOnClickListener(this);
        optionD.setOnClickListener(this);
        firestorer=FirebaseFirestore.getInstance();
        getQuestionlist();
        score=0;
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count.cancel();
                changeQuestion();

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quesnum--;
                quesnum--;
                count.cancel();
                changeQuestion();

            }
        });

    }
    private void getQuestionlist()
    {list.clear();
        firestorer.collection("Quiz")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful())
                {
                    QuerySnapshot questions =task.getResult();
                    Log.d("logg",String.valueOf(questions.size()));
                    for(QueryDocumentSnapshot doc: questions)
                    {
                        list.add(new Questionmodal(doc.getString("Question"),
                                doc.getString("OptionA"),
                                doc.getString("OptionB"),
                                doc.getString("OptionC"),



                                doc.getString("OptionD"),
                                Integer.valueOf(doc.getString("Correct ans"))));
                    }
                    setQuestion();


                       }


                else {
                    Toast.makeText(Questions.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

});



            }


    private void setQuestion()
    {
        timer.setText(String.valueOf(10));
        question.setText(list.get(0).getQuestion());
        optionA.setText(list.get(0).getOptionA());
        optionB.setText(list.get(0).getOptionB());
        optionC.setText(list.get(0).getOptionC());
        optionD.setText(list.get(0).getOptionD());

        num.setText(String.valueOf(1)+"/"+ String.valueOf(list.size()));
        startTimer();
        quesnum=0;


    }
    private void startTimer()
    {
         count=new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                changeQuestion();
            }
        };
        count.start();
    }


        @Override
    public void onClick(View v) {
        int selectedOption=0;
        switch (v.getId())
        {
            case R.id.button5:
                selectedOption=1;
                break;
            case R.id.button6:
                selectedOption=2;
                break;
            case R.id.button7:
                selectedOption=3;
                break;
            case R.id.button8:
                selectedOption=4;
                break;
             default:
        }
        count.cancel();
        checkAnswer(selectedOption,v);
    }
    private void checkAnswer(int selectedOption, final View view)
    {
        if(selectedOption==list.get(quesnum).getCorrectans())
        {
            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(android.graphics.Color.GREEN));
            score++;
        }
        else {
            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            switch (list.get(quesnum).getCorrectans())
            {
                case 1:
                    optionA.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 2:
                    optionB.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 3:
                    optionC.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 4:
                    optionD.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
            }

        }
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                optionA.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                optionB.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                optionC.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                optionD.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                changeQuestion();

            }
        },2000);

    }
    private void changeQuestion()
    {
        if(quesnum<list.size()-1)

        {
            quesnum++;
            question.setText(list.get(quesnum).getQuestion());
            optionA.setText(list.get(quesnum).getOptionA());
            optionB.setText(list.get(quesnum).getOptionB());
            optionC.setText(list.get(quesnum).getOptionC());
            optionD.setText(list.get(quesnum).getOptionD());

            num.setText(String.valueOf(quesnum+1)+"/"+String.valueOf(list.size()));
            timer.setText(String.valueOf(10));
            startTimer();


        }
        else{
            Toast.makeText(Questions.this,"Quiz is finished",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(Questions.this,Score.class);
            intent.putExtra("SCORE",String.valueOf(score)+"/"+String.valueOf(list.size()));
            startActivity(intent);
            Questions.this.finish();
        }
    }
}
