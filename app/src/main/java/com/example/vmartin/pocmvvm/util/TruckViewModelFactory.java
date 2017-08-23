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

package com.example.vmartin.pocmvvm.util;

import android.arch.lifecycle.ViewModelProvider;

import com.example.vmartin.pocmvvm.repository.FoodTruckRepository;
import com.example.vmartin.pocmvvm.ui.foodtrucklist.ListViewModel;

public class TruckViewModelFactory implements ViewModelProvider.Factory {

    private FoodTruckRepository foodTruckRepository;

    public TruckViewModelFactory(FoodTruckRepository foodTruckRepository) {
        this.foodTruckRepository = foodTruckRepository;
    }


    @Override
    public ListViewModel create(Class modelClass) {
        return new ListViewModel(foodTruckRepository);
    }

}