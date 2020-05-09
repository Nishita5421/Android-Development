package com.nishita.notepad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

public class edit_note extends AppCompatActivity {

    private EditText title;
    private EditText body;

    private int i;
    private String s,s1;
private Context context;
CharSequence text="Note Saved";
private int duration=Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);

        setContentView(R.layout.edit_note);

        title=findViewById(R.id.input_title);
        body=findViewById(R.id.input_note);
    }

    @Override
                public void onResume()
        {


            super.onResume();
                 SharedPreferences sh =getSharedPreferences("share",MODE_PRIVATE);
                s=sh.getString("title","");
                s1=sh.getString("body","");
                 //if(title==null) {
                     title.setText(s);
                 //}
                 //if(body==null) {
                     body.setText(s1);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_note_, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
            if(id==R.id.save)
            {
                global.Title=title.getText().toString();
                global.Body=body.getText().toString();

                SharedPreferences sharedPreferences=getSharedPreferences("share",MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("title", global.Title);
                myEdit.putString("body",global.Body);
                myEdit.apply();
                Toast.makeText(this,"Note is Saved",Toast.LENGTH_SHORT).show();
                finish();

            }
        return super.onOptionsItemSelected(item);
    }
public void msg(View view)
{

    Snackbar.make(view, "Note Added", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
}
}
