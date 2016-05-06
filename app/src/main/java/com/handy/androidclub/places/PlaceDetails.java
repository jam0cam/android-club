package com.handy.androidclub.places;


import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jiatse on 4/30/16.
 */
public class PlaceDetails {

    @SerializedName("address_components")
    public List<AddressComponent> addressComponents;

    @Nullable
    public String getPostalCode() {
        try {
            for (AddressComponent component : addressComponents) {
                for (String s : component.types) {
                    if (s.equals("postal_code")) {
                        return component.longName;
                    }
                }
            }

            return null;
        } catch (Exception e) {
            return null;
        }

    }
}
