package com.example.vmartin.pocmvvm.data;

import com.example.vmartin.pocmvvm.model.ResponseOpenData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WebService {

    public static final String ENDPOINT = "https://opendata.bruxelles.be/api/records/1.0/";
    public static final int TIMEOUT_IN_SEC = 15;


    @GET("search?dataset=bxl_food_trucks")
    Call<ResponseOpenData> getAllFoodTruck(@Query("rows") int nbr);

}
