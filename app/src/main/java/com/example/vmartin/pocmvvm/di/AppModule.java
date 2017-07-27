package com.example.vmartin.pocmvvm.di;

import com.example.vmartin.pocmvvm.data.util.LiveDataCallAdapterFactory;
import com.example.vmartin.pocmvvm.data.api.WebService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
class AppModule {


    @Provides
    @Singleton
    WebService provideRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build();

        return retrofit.create(WebService.class);
    }

}
