package com.nishita.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class page2_choose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2_choose);

        ImageView back = (ImageView)findViewById(R.id.back);
        back.bringToFront();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(page2_choose.this, Student_login.class));
            }
        });

        Button log = (Button)findViewById(R.id.log);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(page2_choose.this, teacher_login.class));
            }
        });

        Button log2  = (Button)findViewById(R.id.log2);
        log2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(page2_choose.this, Student_login.class));
            }
        });

        Button reg = (Button)findViewById(R.id.reg);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(page2_choose.this, teacher_register.class));
            }
        });

        Button reg2 = (Button)findViewById(R.id.reg2);
        reg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(page2_choose.this, student_register.class));
            }
        });
    }
}