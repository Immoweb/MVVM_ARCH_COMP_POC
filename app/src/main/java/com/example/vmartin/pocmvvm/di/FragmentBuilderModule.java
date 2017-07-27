package com.example.vmartin.pocmvvm.di;


import com.example.vmartin.pocmvvm.ui.foodtrucklist.ListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract ListFragment contributeListFragment();
}
