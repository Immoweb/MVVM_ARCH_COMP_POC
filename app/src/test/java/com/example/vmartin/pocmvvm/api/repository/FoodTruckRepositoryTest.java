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

package com.example.vmartin.pocmvvm.api.repository;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;

import com.example.vmartin.pocmvvm.data.api.ApiResponse;
import com.example.vmartin.pocmvvm.data.api.WebService;
import com.example.vmartin.pocmvvm.model.Record;
import com.example.vmartin.pocmvvm.repository.FoodTruckRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.vmartin.pocmvvm.api.ApiUtil.successCall;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class FoodTruckRepositoryTest {
    private FoodTruckRepository repository;
    private WebService webService;
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    @Before
    public void init() {
        webService = mock(WebService.class);
        repository = new FoodTruckRepository(webService);
    }

    @Test
    public void loadRepoFromNetwork() throws IOException {

        List<Record> records = new ArrayList<>();
        Record record = new Record();
        record.setRecordid("123");
        records.add(record);
        LiveData<ApiResponse<List<Record>>> call = successCall(records);
        doReturn(call).when(webService).getAllFoodTruck(2);
        assertEquals(call, webService.getAllFoodTruck(2));

    }

}