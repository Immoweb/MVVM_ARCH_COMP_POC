/*
 * Copyright 2017, The Android Open Source Project
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

package com.example.vmartin.pocmvvm.ui;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.vmartin.pocmvvm.R;
import com.example.vmartin.pocmvvm.databinding.ItemFoodTruckBinding;
import com.example.vmartin.pocmvvm.model.Record;

import java.util.List;

public class FoodTruckAdapter extends RecyclerView.Adapter<FoodTruckAdapter.FoodTruckHolder> {

    @Nullable
    private final FoodTruckClickCallback mFoodTruckClickCallback;
    private final DataBindingComponent dataBindingComponent;

    List<? extends Record> mRecords;

    public FoodTruckAdapter(DataBindingComponent dataBindingComponent,
                            @Nullable FoodTruckClickCallback clickCallback) {
        this.dataBindingComponent = dataBindingComponent;
        this.mFoodTruckClickCallback = clickCallback;
    }

    public void setData(final List<? extends Record> mRecords) {
        this.mRecords = mRecords;
        notifyDataSetChanged();
    }

    @Override
    public FoodTruckHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemFoodTruckBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_food_truck,
                        parent, false, dataBindingComponent);
        //binding.setCallback(mFoodTruckClickCallback);
        return new FoodTruckHolder(binding);
    }

    @Override
    public void onBindViewHolder(FoodTruckHolder holder, int position) {
        holder.binding.setRecord(mRecords.get(position));
    }

    @Override
    public int getItemCount() {
        return mRecords == null ? 0 : mRecords.size();
    }

    class FoodTruckHolder extends RecyclerView.ViewHolder {
        private ItemFoodTruckBinding binding;

        FoodTruckHolder(ItemFoodTruckBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
