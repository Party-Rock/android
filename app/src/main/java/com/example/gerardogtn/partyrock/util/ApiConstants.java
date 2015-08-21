package com.example.gerardogtn.partyrock.util;

/**
 * Created by gerardogtn on 8/18/15.
 */
public class ApiConstants {
    public static final String URL_BASE = "http://partyrock-49947.onmodulus.net";

    public static final String API_VERSION = "/v1";

    public static final String VENUES = "/venues";
    public static final String USERS = "/users";
    public static final String RESERVATIONS = "/reservations";


    public static final String VALUE_RENTED_DATE = "/rented";

    // VENUE PARAMS
    public static final String PARAM_NAME = "name";
    public static final String PARAM_LAT = "lat";
    public static final String PARAM_LONG = "long";
    public static final String PARAM_COLONIA = "colonia";
    public static final String PARAM_DESCRIPTION = "description";
    public static final String PARAM_SIZE = "size";
    public static final String PARAM_PRICE = "price";
    public static final String PARAM_CAPACITY = "capacity";

    // USER PARAMS
    public static final String PARAM_FIRST_NAME = "fname";
    public static final String PARAM_LAST_NAME = "lname";
    public static final String PARAM_PASSWORD = "password";

    // RESERVATION PARAMS
    public static final String PARAM_USER_ID = "userId";
    public static final String PARAM_OWNER_ID = "ownerId";
    public static final String PARAM_VENUE_ID = "venueId";
    public static final String PARAM_RENTED_DATE = "rentedDate";

    public static final String PATH_VENUE_ID = "/{venueId}";

    public static final String URL_ALL_VENUES = API_VERSION + VENUES;
    public static final String URL_ALL_USERS = API_VERSION + USERS;
    public static final String URL_ALL_RESERVATIONS = API_VERSION + RESERVATIONS;
    public static final String URL_PATCH_VENUE_RENTED_DATES = API_VERSION + VENUES+ VALUE_RENTED_DATE
            + PATH_VENUE_ID;

}
