package com.hardtask.eid.ahmed.registertask.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hardtask.eid.ahmed.registertask.model.API;
import com.hardtask.eid.ahmed.registertask.model.Country;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountryViewModel extends ViewModel {

    private MutableLiveData<List<Country>> countryList;

    public LiveData<List<Country>> getCountryList() {
        if (countryList == null) {
            countryList = new MutableLiveData<>();
            loadCountries();
        }
        return countryList;
    }

    private void loadCountries() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<List<Country>> call = api.getCountries();
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(@NonNull Call<List<Country>> call, @NonNull Response<List<Country>> response) {
                countryList.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Country>> call, @NonNull Throwable t) {
                Log.e("CountryViewModel","onFailure"+"Error");
            }
        });
    }

}
