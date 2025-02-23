package com.example.simplenotepad;


import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
        import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<String>();
    static ArrayAdapter<String> arrayAdapter;
    private FloatingActionButton fab;
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.aptly, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        super.onOptionsItemSelected(item);

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.listView);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("NOTE_SAVE", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>)sharedPreferences.getStringSet("notes", null);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(arrayAdapter);
        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Editor.class);
                startActivity(intent);
            }
        });
        if(set == null)
        {
            notes.add("Example Note");
        }

        else
        {
            notes = new ArrayList<>(set);         // to bring all the already stored data in the set to the notes ArrayList
        }



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getApplicationContext(), Editor.class);
                intent.putExtra("noteID", position);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id)
            {
                new AlertDialog.Builder(MainActivity.this)                   // we can't use getApplicationContext() here as we want the activity to be the context, not the application
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete?")
                        .setMessage("Are you sure you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)                        // to remove the selected note once "Yes" is pressed
                            {
                                notes.remove(position);
                                arrayAdapter.notifyDataSetChanged();

                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.tanay.thunderbird.deathnote", Context.MODE_PRIVATE);
                                HashSet<String> set = new HashSet<>(MainActivity.notes);
                                sharedPreferences.edit().putStringSet("notes", set).apply();
                            }
                        })

                        .setNegativeButton("No", null)
                        .show();

                return true;               // this was initially false but we change it to true as if false, the method assumes that we want to do a short click after the long click as well
            }
        });
    }
}