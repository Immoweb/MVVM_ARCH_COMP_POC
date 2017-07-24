package com.example.vmartin.pocmvvm.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.vmartin.pocmvvm.model.Record;
import com.example.vmartin.pocmvvm.model.ResponseOpenData;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodTruckRepository {

    private final WebService webService;

    @Inject
    public FoodTruckRepository(WebService webService) {
        this.webService = webService;
    }

    public LiveData<ArrayList<Record>> loadFoodTrucks() {
        final MutableLiveData<ArrayList<Record>> data = new MutableLiveData<>();
        webService.getAllFoodTruck(500).enqueue(new Callback<ResponseOpenData>() {
            @Override
            public void onResponse(Call<ResponseOpenData> call,
                                   Response<ResponseOpenData> response) {
                data.setValue(response.body().getRecords());
            }

            @Override
            public void onFailure(Call<ResponseOpenData> call, Throwable t) {

            }
        });
        return data;
    }

}