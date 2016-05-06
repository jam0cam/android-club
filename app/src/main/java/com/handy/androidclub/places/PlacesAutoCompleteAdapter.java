package com.handy.androidclub.places;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jiatse on 5/5/16.
 */
class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

    private static final String TAG = PlacesAutoCompleteAdapter.class.getName();

    List<String> mPredictionValues;
    List<PlacePrediction> mPredictions;

    Context mContext;
    int mResource;

    PlacesService mPlaceService;

    public PlacesAutoCompleteAdapter(Context context, int resource, PlacesService placesService) {
        super(context, resource);

        mContext = context;
        mResource = resource;
        mPlaceService = placesService;
    }

    @Override
    public int getCount() {
        // Last item will be the footer
        return mPredictionValues.size();
    }

    @Override
    public String getItem(int position) {
        return mPredictionValues.get(position);
    }

    public PlacePrediction getPrediction(int position) {
        return mPredictions.get(position);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    Call<PlacePredictionResponse> placePrediction = mPlaceService.getPlacePrediction(constraint.toString());
                    try {

                        Response<PlacePredictionResponse> response = placePrediction.execute();
                        response.body().filter();
                        mPredictions = response.body().predictions;
                        mPredictionValues = response.body().getDescriptions();
                    } catch (IOException e) {
                        Log.e(TAG, "performFiltering: " + e.getMessage(), e);
                        mPredictionValues = new ArrayList<>();
                        mPredictions = new ArrayList<>();
                    }

                    filterResults.values = mPredictionValues;
                    filterResults.count = mPredictionValues.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }
}