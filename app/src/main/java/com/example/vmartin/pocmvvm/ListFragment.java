package com.example.vmartin.pocmvvm;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vmartin.pocmvvm.binding.FragmentDataBindingComponent;
import com.example.vmartin.pocmvvm.databinding.FragmentListBinding;
import com.example.vmartin.pocmvvm.di.Injectable;
import com.example.vmartin.pocmvvm.model.Record;
import com.example.vmartin.pocmvvm.ui.FoodTruckAdapter;
import com.example.vmartin.pocmvvm.ui.FoodTruckClickCallback;
import com.example.vmartin.pocmvvm.viewmodel.ListViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class ListFragment extends LifecycleFragment implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private FragmentListBinding mBinding;

    private FoodTruckAdapter mFoodTruckAdapter;

    ListViewModel listViewModel;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_list, container, false, dataBindingComponent);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listViewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel.class);
        mFoodTruckAdapter = new FoodTruckAdapter(dataBindingComponent,
                mFoodTruckClickCallback);
        mBinding.recordsList.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recordsList.setAdapter(mFoodTruckAdapter);
        subscribeUiToViewModel();
    }

    private void subscribeUiToViewModel() {
        listViewModel.getFoodTrucks().observe(this, new Observer<ArrayList<Record>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Record> records) {
                mFoodTruckAdapter.setData(records);
            }
        });
    }

    private final FoodTruckClickCallback mFoodTruckClickCallback = new FoodTruckClickCallback() {
        @Override
        public void onClick(Record record) {

            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                //((MainActivity) getActivity()).show(record);
            }
        }
    };
}
