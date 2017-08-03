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

package com.example.vmartin.pocmvvm.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.example.vmartin.pocmvvm.data.Resource;
import com.example.vmartin.pocmvvm.data.api.ApiResponse;
import com.example.vmartin.pocmvvm.data.util.AbsentLiveData;

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 *
 * @param <ResultType>
 * @param <RequestType>
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    NetworkBoundResource() {
        result.setValue(Resource.<ResultType>loading(null));
        final LiveData<ResultType> localSource = loadFromLocal(AbsentLiveData.<RequestType>create
                ());
        result.addSource(localSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType data) {
                result.removeSource(localSource);

                // at the moment, we ALWAYS fetch from network after fetching from Local
                fetchFromNetwork(localSource);
            }
        });
    }

    private void fetchFromNetwork(final LiveData<ResultType> localSource) {

        final LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        // we re-attach the source as a new source, it will dispatch its latest value quickly
        result.addSource(localSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType newData) {
                result.setValue(Resource.loading(newData));
            }
        });

        result.addSource(apiResponse, new Observer<ApiResponse<RequestType>>() {
            @Override
            public void onChanged(@Nullable final ApiResponse<RequestType> response) {

                result.removeSource(apiResponse);
                result.removeSource(localSource);

                //noinspection ConstantConditions
                if (response.isSuccessful()) {

                    saveResultAndReInit(response);
                } else {
                    onFetchFailed();
                    result.addSource(localSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(@Nullable ResultType newData) {
                            result.setValue(Resource.error(response.errorMessage, newData));
                        }
                    });
                }
            }
        });
    }


    @MainThread
    private void saveResultAndReInit(final ApiResponse<RequestType> response) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                if (response.body != null) {
                    saveCallResult(processResponse(response));
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                // we specially request a new live data,
                // otherwise we will get immediately last cached value,
                // which may not be updated with latest results received from network.
                MutableLiveData<RequestType> liveData = new MutableLiveData<>();
                liveData.setValue(response.body);
                result.addSource(loadFromLocal(
                        liveData), new Observer<ResultType>() {
                    @Override
                    public void onChanged(@Nullable ResultType newData) {
                        result.setValue(Resource.success(newData));
                    }
                });
            }
        }.execute();
    }

    protected void onFetchFailed() {
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    protected RequestType processResponse(ApiResponse<RequestType> response) {
        return response.body;
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected abstract LiveData<ResultType> loadFromLocal(LiveData<RequestType> liveData);

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();
}
