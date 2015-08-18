package com.example.gerardogtn.partyrock.service;

import com.example.gerardogtn.partyrock.data.model.VenuesResponse;
import com.example.gerardogtn.partyrock.util.ApiConstants;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by gerardogtn on 8/18/15.
 */
public interface PartyRockApiService {

    @GET(ApiConstants.URL_ALL_VENUES)
    void getAllVenues(Callback<VenuesResponse> callback);
}
