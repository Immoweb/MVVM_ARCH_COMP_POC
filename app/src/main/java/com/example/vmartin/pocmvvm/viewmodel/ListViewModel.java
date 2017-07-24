package com.example.vmartin.pocmvvm.viewmodel;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.vmartin.pocmvvm.data.FoodTruckRepository;
import com.example.vmartin.pocmvvm.model.Record;

import java.util.ArrayList;

import javax.inject.Inject;

public class ListViewModel extends ViewModel {

    private final LiveData<ArrayList<Record>> mObservableData;

    @Inject
    public ListViewModel(FoodTruckRepository repository) {
        mObservableData = repository.loadFoodTrucks();
    }

    /**
     * Expose the LiveData so the UI can observe it.
     */
    public LiveData<ArrayList<Record>> getFoodTrucks() {
        return mObservableData;
    }

}
