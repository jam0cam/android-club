package com.handy.androidclub.places;

/**
 * Created by jiatse on 5/5/16.
 */

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jiatse on 4/26/16.
 */
public interface PlacesService {

    @GET("maps/api/place/autocomplete/json?types=address")
    Call<PlacePredictionResponse> getPlacePrediction(@Query("input") String input);

    @GET("maps/api/place/details/json?types=address")
    Call<PlaceDetailsResponse> getPlaceDetails(@Query("placeid") String placeId);
}
