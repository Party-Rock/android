package com.example.gerardogtn.partyrock.service;

import com.example.gerardogtn.partyrock.data.model.User;
import com.example.gerardogtn.partyrock.data.model.Venue;
import com.example.gerardogtn.partyrock.util.ApiConstants;
import com.google.android.gms.common.api.Api;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by gerardogtn on 8/18/15.
 */
public interface PartyRockApiService {

    @GET(ApiConstants.URL_ALL_VENUES)
    void getAllVenues(Callback<List<Venue>> callback);

    @FormUrlEncoded
    @POST(ApiConstants.URL_ALL_USERS)
    void postUser(@Field(ApiConstants.PARAM_FIRST_NAME) String firstName,
                  @Field(ApiConstants.PARAM_LAST_NAME) String lastName,
                  @Field(ApiConstants.PARAM_PASSWORD) String password,
                  Callback<Response> responseCallback);

    @FormUrlEncoded
    @POST(ApiConstants.URL_ALL_RESERVATIONS)
    void postReservation(@Field(ApiConstants.PARAM_USER_ID) String userId,
                         @Field(ApiConstants.PARAM_OWNER_ID) String ownerId,
                         @Field(ApiConstants.PARAM_VENUE_ID) String venueId,
                         @Field(ApiConstants.PARAM_RENTED_DATE) String dateIso,
                         Callback<Response> responseCallback);

}
