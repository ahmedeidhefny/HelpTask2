package com.hardtask.eid.ahmed.registertask.viewModel;

import com.hardtask.eid.ahmed.registertask.model.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CityViewModel {
    private Retrofit retrofit ;

    public Retrofit getCities(){
        retrofit = new Retrofit.Builder()
                .baseUrl(API.Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit ;
    }
}
