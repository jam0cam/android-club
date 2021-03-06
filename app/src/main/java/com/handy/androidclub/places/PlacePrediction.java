package com.handy.androidclub.places;

/**
 * Created by jiatse on 5/5/16.
 */

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * This holds the response for getting a bunch of places that matches what the user already typed.
 *
 * Created by jiatse on 4/30/16.
 */
public class PlacePrediction implements Serializable {

    @SerializedName("place_id")
    public String placeId;
    public String description;

    public List<Map<String, String>> terms;
    public List<String> types;

    @Nullable
    public String getAddress() {
        try {
            return terms.get(0).get("value") + " " +  terms.get(1).get("value");
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public String getCity() {
        try {
            return terms.get(2).get("value");
        } catch (Exception e) {
            return null;
        }
    }


    @Nullable
    public String getState() {
        try {
            return terms.get(3).get("value");
        } catch (Exception e) {
            return null;
        }
    }

}
