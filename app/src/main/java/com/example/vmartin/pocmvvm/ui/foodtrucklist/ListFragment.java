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
import com.example.vmartin.pocmvvm.databinding.FragmentListBinding;
import com.example.vmartin.pocmvvm.di.Injectable;
import com.example.vmartin.pocmvvm.model.Record;
import com.example.vmartin.pocmvvm.ui.common.RetryCallback;

import java.util.List;

import javax.inject.Inject;


public class ListFragment extends LifecycleFragment implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private FragmentListBinding mBinding;

    private FoodTruckAdapter mFoodTruckAdapter;

    ListViewModel listViewModel;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
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
