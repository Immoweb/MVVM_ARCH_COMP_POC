package com.example.vmartin.pocmvvm.ui.foodtrucklist;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vmartin.pocmvvm.R;
import com.example.vmartin.pocmvvm.binding.FragmentDataBindingComponent;
import com.example.vmartin.pocmvvm.data.Resource;
import com.example.vmartin.pocmvvm.data.api.WebService;
import com.example.vmartin.pocmvvm.data.util.LiveDataCallAdapterFactory;
import com.example.vmartin.pocmvvm.databinding.FragmentListBinding;
import com.example.vmartin.pocmvvm.model.Record;
import com.example.vmartin.pocmvvm.repository.FoodTruckRepository;
import com.example.vmartin.pocmvvm.ui.common.RetryCallback;
import com.example.vmartin.pocmvvm.util.TruckViewModelFactory;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ListFragment extends LifecycleFragment {

    ViewModelProvider.Factory viewModelFactory;

    private FragmentListBinding mBinding;

    private FoodTruckAdapter mFoodTruckAdapter;

    WebService webService;

    ListViewModel listViewModel;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        webService = new Retrofit.Builder()
                .baseUrl(WebService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build().create(WebService.class);

        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_list, container, false, dataBindingComponent);
        mBinding.setRetryCallback(new RetryCallback() {
            @Override
            public void retry() {
                listViewModel.retry();
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModelFactory = new TruckViewModelFactory(new FoodTruckRepository(webService));
        listViewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel.class);
        mFoodTruckAdapter = new FoodTruckAdapter(dataBindingComponent,
                mFoodTruckClickCallback);
        mBinding.recordsList.setAdapter(mFoodTruckAdapter);
        listViewModel.setParam(150);
        subscribeUiToViewModel();
    }

    private void subscribeUiToViewModel() {
        listViewModel.getFoodTrucks().observe(this, new Observer<Resource<List<Record>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Record>> records) {
                mBinding.setListResource(records);
                if (records != null) {
                    mFoodTruckAdapter.setData(records.data);
                }
            }
        });

    }

    private final FoodTruckClickCallback mFoodTruckClickCallback = new FoodTruckClickCallback() {
        @Override
        public void onClick(Record record) {
            //TODO : send to details
        }
    };
}
