/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.vmartin.pocmvvm.api.viewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.example.vmartin.pocmvvm.data.Resource;
import com.example.vmartin.pocmvvm.model.Record;
import com.example.vmartin.pocmvvm.repository.FoodTruckRepository;
import com.example.vmartin.pocmvvm.ui.foodtrucklist.ListViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(JUnit4.class)
public class ListViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private FoodTruckRepository repository;
    private ListViewModel listViewModel;

    @Before
    public void setup() {
        repository = mock(FoodTruckRepository.class);
        listViewModel = new ListViewModel(repository);
    }


    @Test
    public void testNull() {
        assertThat(listViewModel.getFoodTrucks(), notNullValue());
        assertThat(listViewModel.getFoodTrucks(), notNullValue());
        verify(repository, never()).loadFoodTrucks(anyInt());
    }

    @Test
    public void dontFetchWithoutObservers() {
        // even if we setParam, we don't start fetching because no observe
        listViewModel.setParam(3);
        verify(repository, never()).loadFoodTrucks(anyInt());
    }

    @Test
    public void fetchWhenObserved() {
        ArgumentCaptor<Integer> param = ArgumentCaptor.forClass(Integer.class);

        listViewModel.setParam(3);
        listViewModel.getFoodTrucks().observeForever(mock(Observer.class));
        verify(repository, times(1)).loadFoodTrucks(param.capture());
        assertThat(param.getValue(), is(3));
    }

    @Test
    public void changeWhileObserved() {
        ArgumentCaptor<Integer> param = ArgumentCaptor.forClass(Integer.class);

        listViewModel.getFoodTrucks().observeForever(mock(Observer.class));

        listViewModel.setParam(3);
        listViewModel.setParam(4);

        verify(repository, times(2)).loadFoodTrucks(param.capture());
        assertThat(param.getAllValues(), is(Arrays.asList(3,4)));
    }

    @Test
    public void loadFoodTrucks() {
        Observer<Resource<List<Record>>> observer = mock(Observer.class);
        listViewModel.getFoodTrucks().observeForever(observer);
        verifyNoMoreInteractions(observer);
        verifyNoMoreInteractions(repository);
        listViewModel.setParam(3);
        verify(repository).loadFoodTrucks(3);
    }

    @Test
    public void retry() {
        listViewModel.retry();
        verifyNoMoreInteractions(repository);
        listViewModel.setParam(6);
        verifyNoMoreInteractions(repository);
        Observer<Resource<List<Record>>> observer = mock(Observer.class);
        listViewModel.getFoodTrucks().observeForever(observer);
        verify(repository).loadFoodTrucks(6);
        reset(repository);
        listViewModel.retry();
        verify(repository).loadFoodTrucks(6);
    }

}