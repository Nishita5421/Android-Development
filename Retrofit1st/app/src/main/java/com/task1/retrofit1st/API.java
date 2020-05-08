package com.task1.retrofit1st;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {
    String BASE_URL ="https://rentbaaz.herokuapp.com/";

    @GET("adgMarvel")
    Call<List<Marvel>> getAvengers();
}
