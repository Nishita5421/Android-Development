package com.nishita.notepad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

public class edit_note extends AppCompatActivity {

    private EditText title;
    private EditText body;
private Context context;
CharSequence text="Note Saved";
private int duration=Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);

        setContentView(R.layout.edit_note);


        title=findViewById(R.id.input_title);
        body=findViewById(R.id.input_note);}

        @Override
                protected void onResume()
        {
            super.onResume();
            @SuppressLint("WrongConstant") SharedPreferences sh =getSharedPreferences("share",MODE_APPEND);
                String s=sh.getString("title","");
                String s1=sh.getString("body","");
                title.setText(s);
                body.setText(s1);


    }

    @Override
    protected void onPause()
    {
        super.onPause();


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
                onPause();
                SharedPreferences sharedPreferences=getSharedPreferences("share",MODE_PRIVATE);
                SharedPreferences .Editor myEdit = sharedPreferences.edit();
                myEdit.putString("title",title.getText().toString());
                myEdit.putString("body",body.getText().toString());
                myEdit.commit();

                Toast.makeText(this,"Note is Saved",Toast.LENGTH_SHORT).show();


            }
        return super.onOptionsItemSelected(item);
    }
public void msg(View view)
{

    Snackbar.make(view, "Note Added", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
}
}
