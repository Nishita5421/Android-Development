package com.nishita.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView=findViewById(R.id.list_item);
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("https://rentbaaz.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api=retrofit.create(API.class);
        Call<List<Marvel>> call= api.getHeroes();
        call.enqueue(new Callback<List<Marvel>>() {
            @Override
            public void onResponse(Call<List<Marvel>> call, Response<List<Marvel>> response) {


                List<Marvel> heroes =response.body();
                String[] heronames=new String[heroes.size()];

                String[] realnames=new String[heroes.size()];
                String[] teams=new String[heroes.size()];
                String[] first=new String[heroes.size()];
                String[] createdby=new String[heroes.size()];
                String[] publish=new String[heroes.size()];
                String[] bio=new String[heroes.size()];
                for(int i=0;i<heroes.size();i++)
                {
                    heronames[i]=heroes.get(i).getName();
                    realnames[i]=heroes.get(i).getRealname();
                    teams[i]=heroes.get(i).getTeam();
                    first[i]=heroes.get(i).getFirstappearance();
                    createdby[i]=heroes.get(i).getCreatedby();
                    publish[i]=heroes.get(i).getPublisher();
                    bio[i]=heroes.get(i).getBio();

                }
                listView.setAdapter(
                        new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,heronames)

                );
              SharedPreferences sharedPreferences=getSharedPreferences("Share",MODE_PRIVATE);
              SharedPreferences.Editor editor=sharedPreferences.edit();
                Gson gson=new Gson();

                editor.putString("Name",gson.toJson(heronames));
                editor.putString("Real",gson.toJson(realnames));
                editor.putString("Tea",gson.toJson(teams));
                editor.putString("Fir",gson.toJson(first));
                editor.putString("Cre",gson.toJson(createdby));
                editor.putString("Pub",gson.toJson(publish));
                editor.putString("Bio",gson.toJson(bio));
                editor.apply();


                           }

            @Override
            public void onFailure(Call<List<Marvel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
