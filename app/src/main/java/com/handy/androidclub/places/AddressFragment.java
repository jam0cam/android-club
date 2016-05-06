package com.handy.androidclub.places;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.handy.androidclub.R;
import com.handy.androidclub.core.App;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jiatse on 5/5/16.
 */
public class AddressFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = AddressFragment.class.getName();
    private GoogleApiClient mGoogleApiClient;


    @Bind(R.id.main_layout)
    View mMainLayout;

    @Bind(R.id.text_address_1)
    TextView mAddress1;

    @Bind(R.id.edit_address)
    AutoCompleteTextView mAutoCompleteAddress;

    @Bind(R.id.edit_city)
    TextInputEditText mEditCity;

    @Bind(R.id.edit_state)
    TextInputEditText mEditState;

    @Bind(R.id.edit_zip)
    TextInputEditText mEditZip;

    PlacesService mPlacesService;
    PlacesAutoCompleteAdapter mAutoCompleteAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        mPlacesService = ((App)getActivity().getApplication()).getPlacesService();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_address, container, false);
        ButterKnife.bind(this, v);

        SupportPlaceAutocompleteFragment autocompleteFragment = (SupportPlaceAutocompleteFragment)
                getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                mAddress1.setText(place.getAddress());
            }

            @Override
            public void onError(Status status) {
                String msg = "An error occurred: " + status;
                Log.i(TAG, msg);
                Snackbar.make(mMainLayout, msg, Snackbar.LENGTH_LONG).show();
            }
        });

        mAutoCompleteAdapter = new PlacesAutoCompleteAdapter(
                getActivity(),
                R.layout.auto_complete_list_item,
                mPlacesService
        );

        mAutoCompleteAddress.setAdapter(mAutoCompleteAdapter);
        mAutoCompleteAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PlacePrediction prediction = mAutoCompleteAdapter.getPrediction(position);
                mAutoCompleteAddress.setText(prediction.getAddress());
                mEditCity.setText(prediction.getCity());
                mEditState.setText(prediction.getState());

                populateFields(prediction);
                mMainLayout.requestFocus();

                hideKeyboard();
            }
        });
        return v;
    }


    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)getActivity()
                .getSystemService(Activity.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    public void populateFields(final PlacePrediction prediction) {
        final Call<PlaceDetailsResponse> placeDetails = mPlacesService.getPlaceDetails(prediction.placeId);
        placeDetails.enqueue(new Callback<PlaceDetailsResponse>() {
            @Override
            public void onResponse(Call<PlaceDetailsResponse> call, Response<PlaceDetailsResponse> response) {
                mEditZip.setText(response.body().result.getPostalCode());
            }

            @Override
            public void onFailure(Call<PlaceDetailsResponse> call, Throwable t) {
                Snackbar.make(mMainLayout, "onFailure: " + t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Snackbar.make(mMainLayout, connectionResult.getErrorMessage(), Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.btn_clear)
    public void clicked(View view) {
        mAutoCompleteAddress.setText("");
        mEditCity.setText("");
        mEditState.setText("");
        mEditZip.setText("");
    }
}
