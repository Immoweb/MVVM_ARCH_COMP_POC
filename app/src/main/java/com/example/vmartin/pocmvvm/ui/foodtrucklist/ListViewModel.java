package com.example.vmartin.pocmvvm.ui.foodtrucklist;


import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.vmartin.pocmvvm.data.Resource;
import com.example.vmartin.pocmvvm.data.util.AbsentLiveData;
import com.example.vmartin.pocmvvm.model.Record;
import com.example.vmartin.pocmvvm.repository.FoodTruckRepository;

import java.util.List;

import javax.inject.Inject;

public class ListViewModel extends ViewModel {

    private final MutableLiveData<Integer> param = new MutableLiveData<>();

    private final LiveData<Resource<List<Record>>> mObservableData;

    @Inject
    ListViewModel(final FoodTruckRepository repository) {
        mObservableData = Transformations.switchMap(param, new Function<Integer,
                LiveData<Resource<List<Record>>>>() {

            @Override
            public LiveData<Resource<List<Record>>> apply(Integer input) {
                if (input == null) {
                    return AbsentLiveData.create();
                } else {
                    return repository.loadFoodTrucks(input);

                }
            }
        });
    }

    /**
     * Expose the LiveData so the UI can observe it.
     */
    public LiveData<Resource<List<Record>>> getFoodTrucks() {
        return mObservableData;
    }

    public void setParam(@NonNull Integer input) {
        param.setValue(input);
    }

    public void retry() {
        if (this.param.getValue() != null) {
            this.param.setValue(this.param.getValue());
        }
    }

}
