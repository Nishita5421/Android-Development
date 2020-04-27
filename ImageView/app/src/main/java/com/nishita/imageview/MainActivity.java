package com.nishita.imageview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import static android.widget.ImageView.*;

public class MainActivity extends AppCompatActivity {
    static int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView imageview = (ImageView) findViewById(R.id.imView);
        Button left = (Button) findViewById(R.id.left);
        final Button right = (Button) findViewById(R.id.right);
        final int[] imageArray = new int[4];
        imageArray[1] = R.drawable.app_2;
        imageArray[2] = R.drawable.app_3;
        imageArray[3] = R.drawable.app_4;
        imageArray[0] = R.drawable.app_1;
        final TextView textView=(TextView)findViewById(R.id.textView);
        final Animation anim=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim);

        left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                i--;
                if(i==-1)
                {i=3;}
                imageview.startAnimation(anim);
                imageview.setImageResource(imageArray[i]);
                textView.startAnimation(anim);
                textView.setText("Image"+(i+1));
            }});
            right.setOnClickListener(new OnClickListener(){
                @Override
                        public void onClick(View v){
                    i++;
                    if(i==4)
                    {i=0;}
                    imageview.startAnimation(anim);
                    imageview.setImageResource(imageArray[i]);
                    textView.startAnimation(anim);
                    textView.setText("Image"+(i+1));
                }
            }

        );};




    }

