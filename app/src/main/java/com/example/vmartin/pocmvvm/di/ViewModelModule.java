package com.example.vmartin.pocmvvm.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.vmartin.pocmvvm.TruckViewModelFactory;
import com.example.vmartin.pocmvvm.viewmodel.ListViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel.class)
    abstract ViewModel bindListViewModel(ListViewModel movieListViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(TruckViewModelFactory factory);
}
