package com.example.gerardogtn.partyrock.service;

import com.example.gerardogtn.partyrock.util.ApiConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by gerardogtn on 8/18/15.
 */
public class PartyRockApiClient {
    private static PartyRockApiService apiService;

    public static PartyRockApiService getInstance() {
        if (apiService == null) {
            Gson gsonDateBuilder = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                    .create();

            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint(ApiConstants.URL_BASE)
                    .setConverter(new GsonConverter(gsonDateBuilder))
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .build();

            apiService = adapter.create(PartyRockApiService.class);
        }

        return apiService;
    }
}
