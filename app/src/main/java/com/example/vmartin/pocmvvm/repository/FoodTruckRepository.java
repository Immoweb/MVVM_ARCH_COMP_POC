package com.example.vmartin.pocmvvm.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.vmartin.pocmvvm.data.Resource;
import com.example.vmartin.pocmvvm.data.api.ApiResponse;
import com.example.vmartin.pocmvvm.data.api.WebService;
import com.example.vmartin.pocmvvm.model.Record;
import com.example.vmartin.pocmvvm.model.ResponseOpenData;

import java.util.List;

import javax.inject.Inject;

public class FoodTruckRepository {

    private final WebService webService;

    @Inject
    public FoodTruckRepository(WebService webService) {
        this.webService = webService;
    }

    public LiveData<Resource<List<Record>>> loadFoodTrucks(final Integer input) {

        return new NetworkBoundResource<List<Record>, ResponseOpenData>() {

            @Override
            protected void saveCallResult(@NonNull ResponseOpenData item) {
            }

            @Override
            protected LiveData<List<Record>> loadFromLocal(LiveData<ResponseOpenData> res) {
                return Transformations.map(res, new Function<ResponseOpenData, List<Record>>() {
                    @Override
                    public List<Record> apply(ResponseOpenData input) {
                        if (input == null) {
                            return null;
                        } else {
                            return input.getRecords();
                        }
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseOpenData>> createCall() {
                Log.d("pouet ", "loadNetwork");
                return webService.getAllFoodTruck(input);
            }

            @Override
            protected void onFetchFailed() {
            }
        }.asLiveData();

    }

}