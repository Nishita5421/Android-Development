package com.nishita.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import java.util.Objects;

import static java.lang.Thread.sleep;

public class Questions extends AppCompatActivity implements View.OnClickListener {
    private TextView question,num,timer;
    private Button optionA,optionB,optionC,optionD;
    private Button back,next;
    private List<Questionmodal> list;
    static int quesnum;
    private CountDownTimer count;
    private int score,notattempted,correct,notcorrect;
    private FirebaseFirestore firestorer;
    private int catno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestorer=FirebaseFirestore.getInstance();

        setContentView(R.layout.activity_questions);

         catno=getIntent().getIntExtra("Questions",1);
        question=findViewById(R.id.QuestionBar);
       // back=findViewById(R.id.back);
        next=findViewById(R.id.next);
        num=findViewById(R.id.num);
        optionA=findViewById(R.id.button1);
        optionB=findViewById(R.id.button2);
        optionC=findViewById(R.id.button3);
        optionD=findViewById(R.id.button4);
        timer=findViewById(R.id.timer);
        optionA.setOnClickListener(this);
        optionB.setOnClickListener(this);
        optionC.setOnClickListener(this);
        optionD.setOnClickListener(this);


        score=0;
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // count.cancel();
                changeQuestion();

            }
        });
        /*back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quesnum--;
                quesnum--;
                //count.cancel();
                changeQuestion();

            }
        });*/
    getQuestionlist();
    }

    private void getQuestionlist()
    {
        list=new ArrayList<>();
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
                                doc.getString("Option A"),
                                doc.getString("Option B"),
                                doc.getString("Option C"),



                                doc.getString("Option D"),
                                Integer.valueOf((doc.getString("Correct ans")))));
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

        //num.setText(String.valueOf(1)+"/"+ String.valueOf(list.size()));
        num.setText(String.valueOf(1));
        startTimer();
        quesnum=0;


    }
    int minutesToGo = 9;
    int secondsToGo = 60;

    int millisToGo = secondsToGo*1000+minutesToGo*1000*60;
    private void startTimer()
    {
         count=new CountDownTimer(millisToGo,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000) % 60 ;
                int minutes = (int) ((millisUntilFinished / (1000*60)) % 60);
                String text = String.format(" %02d:%02d ",minutes,seconds);
                timer.setText(text);
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
            case R.id.button1:
                optionA.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FEBB95")));
                selectedOption=1;
                break;
            case R.id.button2:
                selectedOption=2;
                optionB.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FEBB95")));
                break;
            case R.id.button3:
                selectedOption=3;
                optionC.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FEBB95")));
                break;
            case R.id.button4:
                selectedOption=4;
                optionD.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FEBB95")));
                break;
             default:
                 notattempted++;
        }

        //count.cancel();
        checkAnswer(selectedOption,v);
    }
    private void checkAnswer(int selectedOption, final View view)
    {
        if(selectedOption==list.get(quesnum).getCorrectans())
        {
           // ((Button)view).setBackgroundTintList(ColorStateList.valueOf(android.graphics.Color.GREEN));
            score++;
            correct++;
        }
        else {
            notcorrect++;
          /*  ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.RED));
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
            }*/

        }
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                optionA.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFDFD5CA")));
                optionB.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFDFD5CA")));
                optionC.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFDFD5CA")));
                optionD.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFDFD5CA")));
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

           // num.setText(String.valueOf(quesnum+1)+"/"+String.valueOf(list.size()));
            num.setText(String.valueOf(quesnum+1));
            //timer.setText(String.valueOf(10));
            //startTimer();


        }
        else{
            final View mView = getLayoutInflater().inflate(R.layout.activity_submit_box, null);
            final AlertDialog.Builder alert = new AlertDialog.Builder(Questions.this);
            alert.setView(mView); //To set the entered text
            final AlertDialog alertDialog = alert.create();
            Button submit1 = (Button)mView.findViewById(R.id.submit1);
            Button review = (Button)mView.findViewById(R.id.review);

            submit1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    notattempted=correct+notcorrect;
                    Toast.makeText(Questions.this,"Quiz is finished",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Questions.this,Score.class);
                    intent.putExtra("SCORE",String.valueOf(score)+"/"+String.valueOf(list.size()));
                    intent.putExtra("attem",String.valueOf(notattempted));
                    intent.putExtra("notattem",String.valueOf(list.size()-notattempted));
                    intent.putExtra("correct",String.valueOf(correct));
                    intent.putExtra("wrong",String.valueOf(notcorrect));
                    startActivity(intent);
                    Questions.this.finish();

                }
            });
            review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });


            alertDialog.show();
        }

        }
    }


