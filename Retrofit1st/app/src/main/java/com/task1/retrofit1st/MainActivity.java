package com.task1.retrofit1st;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import okhttp3.Call.Factory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listView=findViewById(R.id.listview);
        Retrofit retrofit = new Retrofit().Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api=retrofit.create(API.class);
        Call<List<Marvel>> call=api.getAvengers();

        call.enqueue(new Callback<List<Marvel>>() {
            @Override
            public void onResponse(Call<List<Marvel>> call, Response<List<Marvel>> response) {
            List<Marvel> heroes=response.body();

            String[] heronames=new String[heroes.size()];

            for(int i=0;i<heroes.size();i++)
            {
                heronames[i]=heroes.get(i).getName();

            }

            listView.setAdapter(
                    new ArrayAdapter<String>(
                            getApplicationContext(),
                            android.R.layout.simple_list_item_1,
                            heronames
                    )
            );

            for(Marvel h:heroes)
            {
                Log.d("name",h.getName());
                Log.d("realname",h.getRealname());
                Log.d("imageurl",h.getImageurl());
                Log.d("bio",h.getBio());
                Log.d("publisher",h.getPublisher());

            }
            }

            @Override
            public void onFailure(Call<List<Marvel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }
}
