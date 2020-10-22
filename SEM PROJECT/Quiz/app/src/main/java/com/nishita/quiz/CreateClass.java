package com.nishita.quiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class CreateClass extends AppCompatActivity {

    String st1,st2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);

        ImageView back = (ImageView)findViewById(R.id.back);
        back.bringToFront();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateClass.this, teacher_register.class));
            }
        });

        Button ViewCourse2 = findViewById(R.id.ViewCourse2);
        ViewCourse2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateClass.this, ClassCreated.class));
            }
        });
    }

    public void create_class (View view){

        final AlertDialog.Builder alert = new AlertDialog.Builder(CreateClass.this);
        final View mView = getLayoutInflater().inflate(R.layout.activity_class_code, null);

        Button create = (Button)mView.findViewById(R.id.create);
        Button cancel = (Button)mView.findViewById(R.id.cancel);

        alert.setView(mView); //To set the entered text

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);   //To not get canceled if the user touches anywhere else

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText EnterCourseCode = mView.findViewById(R.id.EnterClassName);
                Intent i1 = new Intent(CreateClass.this, ClassCreated.class);
                st1 = EnterCourseCode.getText().toString();
                i1.putExtra("Code1",st1);
                startActivity(i1);
                finish();

                EditText EnterAccessCode = mView.findViewById(R.id.EnterAccessCode);
                Intent i2 = new Intent(CreateClass.this, ClassCreated.class);
                st2 = EnterAccessCode.getText().toString();
                i2.putExtra("Code2",st2);
                startActivity(i2);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
}