package com.hardtask.eid.ahmed.registertask.model;


import com.hardtask.eid.ahmed.registertask.model.City;
import com.hardtask.eid.ahmed.registertask.model.Country;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    String Base_URL = "http://souq.hardtask.co/app/app.asmx/";

    @GET("GetCountries")
    Call<List<Country>> getCountries();

    @GET("GetCities")
    Call<List<City>> getCites(@Query("countryId") String countryId );

}
