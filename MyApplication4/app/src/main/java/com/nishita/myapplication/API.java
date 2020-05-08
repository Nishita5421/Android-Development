package com.nishita.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {


    @GET("adgMarvel")
    Call<List<Marvel>> getHeroes();
}
