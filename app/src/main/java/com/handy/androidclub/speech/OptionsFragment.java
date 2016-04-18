package com.handy.androidclub.speech;

/**
 * Created by jtse on 4/18/16.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.handy.androidclub.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jiatse on 4/10/16.
 */
public class OptionsFragment extends DialogFragment {

    private static final String SELECTED_INDEX = "selected-index";
    int mSelectedIndex;

    OptionsResponse mCallback;

    List<String> mKeys;

    interface OptionsResponse {
        void done(String selectedString);
    }

    public static OptionsFragment newInstance(int idx) {
        OptionsFragment fragment = new OptionsFragment();
        Bundle args = new Bundle();
        args.putInt(SELECTED_INDEX, idx);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mKeys = Arrays.asList(getResources().getStringArray(R.array.language_keys));
        mSelectedIndex = getArguments().getInt(SELECTED_INDEX);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (getParentFragment() instanceof OptionsResponse) {
            mCallback = (OptionsResponse) getParentFragment();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle(R.string.select_a_language)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(R.array.languages, mSelectedIndex, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mSelectedIndex = which;
                            }
                        }
                )
                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (mCallback != null) {
                            mCallback.done(mKeys.get(mSelectedIndex));
                        }
                    }
                });
        return builder.create();
    }
}
