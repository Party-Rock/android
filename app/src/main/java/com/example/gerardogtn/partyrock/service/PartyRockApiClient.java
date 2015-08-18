package com.example.gerardogtn.partyrock.service;

import com.example.gerardogtn.partyrock.util.ApiConstants;

import retrofit.RestAdapter;

/**
 * Created by gerardogtn on 8/18/15.
 */
public class PartyRockApiClient {
    private static PartyRockApiService apiService;

    public static PartyRockApiService getInstance() {
        if (apiService == null) {
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint(ApiConstants.URL_BASE)
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .build();

            apiService = adapter.create(PartyRockApiService.class);
        }

        return apiService;
    }
}
