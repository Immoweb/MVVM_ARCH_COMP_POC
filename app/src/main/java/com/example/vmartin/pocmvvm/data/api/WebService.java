package com.example.vmartin.pocmvvm.data.api;

import android.arch.lifecycle.LiveData;

import com.example.vmartin.pocmvvm.model.ResponseOpenData;

import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WebService {

    String ENDPOINT = "https://opendata.bruxelles.be/api/records/1.0/";
    int TIMEOUT_IN_SEC = 15;


    @GET("search?dataset=bxl_food_trucks")
    LiveData<ApiResponse<ResponseOpenData>> getAllFoodTruck(@Query("rows") int nbr);

}
