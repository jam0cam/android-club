package com.handy.androidclub.places;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jiatse on 4/30/16.
 */
public class AddressComponent {

    @SerializedName("long_name")
    public String longName;

    public List<String> types;
}
