package com.nishita.task2;


import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;
import com.wang.avi.AVLoadingIndicatorView;

import java.time.Month;
import java.time.Year;
import java.util.Calendar;


public abstract class MainActivity extends AppCompatActivity {
    CoordinatorLayout coordinatorLayout;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    int year;
    int month;
    int dayOfMonth;
    AVLoadingIndicatorView avi;
    CountDownTimer ct;
    Button date1,progress,snack,alert,toast;
    Snackbar snack1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button date1 = (Button) findViewById(R.id.button);
        final Button progress = (Button)findViewById(R.id.button5);
        final Button snack = (Button) findViewById(R.id.button2);
        final Button alert = (Button) findViewById(R.id.button4);
        final Button toast = (Button) findViewById(R.id.button3);
        avi=findViewById(R.id.AV1);

        date1.setOnClickListener(new OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         calendar = Calendar.getInstance();
                                         year = calendar.get(Calendar.YEAR);
                                         month = calendar.get(Calendar.MONTH);
                                         dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                                         datePickerDialog = new DatePickerDialog(MainActivity.this,
                                                 new DatePickerDialog.OnDateSetListener() {
                                                     @Override
                                                     public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                                         date1.setText(dayOfMonth + "/" + (++month) + "/" + year);
                                                     }
                                                 }, year, month, dayOfMonth);
                                         datePickerDialog.show();
                                     }
                                 }
        );
        progress.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                CountDownTimer ct=new CountDownTimer(1000,5000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        avi.show();
                    }

                    @Override
                    public void onFinish() {
                    avi.hide();
                    }
                }.start();

            }
        });

        snack.setOnClickListener(new OnClickListener()
                                 {
                                     @Override
                                     public void onClick(View v) {
                                        Snackbar snack1= Snackbar.make(coordinatorLayout,"MESSAGE IS SEEN",Snackbar.LENGTH_LONG);
                                        snack1.show();
                                     }
                                 }
        );

    }
}