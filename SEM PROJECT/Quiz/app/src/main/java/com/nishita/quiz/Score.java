package com.nishita.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Score extends AppCompatActivity {
    private TextView score,wrong,correct,notattempt,attempt;
    private Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        score=findViewById(R.id.marks3);
        done=findViewById(R.id.done);
        wrong=findViewById(R.id.marks2);
        correct=findViewById(R.id.marks);
        notattempt=findViewById(R.id.notattempt);
        attempt=findViewById(R.id.attempt);

        String score_str=getIntent().getStringExtra("SCORE");
        String correcta=getIntent().getStringExtra("correct");
        String notcorrect=getIntent().getStringExtra("wrong");
        String attempted=getIntent().getStringExtra("attem");
        String notattempted=getIntent().getStringExtra("notattem");

        score.setText(score_str);
        wrong.setText(notcorrect);
        correct.setText(correcta);
        notattempt.setText(notattempted);
        attempt.setText(attempted);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Score.this,QuizDetails.class);
                Score.this.startActivity(intent);
                Score.this.finish();
            }
        });

    }
}
