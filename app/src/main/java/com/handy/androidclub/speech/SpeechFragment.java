package com.handy.androidclub.speech;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.handy.androidclub.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jtse on 4/15/16.
 */
public class SpeechFragment extends Fragment implements OptionsFragment.OptionsResponse{

    private final int REQ_CODE_SPEECH_INPUT = 100;


    private List<String> mLanguages;
    private List<String> mKeys;

    private int mSelectedIndex;

    @Bind(R.id.tv)
    TextView mTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_speech, container, false);
        ButterKnife.bind(this, view);


        mKeys = Arrays.asList(getResources().getStringArray(R.array.language_keys));
        mLanguages = Arrays.asList(getResources().getStringArray(R.array.languages));

        updateTitle();
        setHasOptionsMenu(true);
        return view;
    }

    private void updateTitle() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mLanguages.get(mSelectedIndex));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.speech_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_options) {
            showPicker();
        }

        return super.onOptionsItemSelected(item);
    }


    private void showPicker() {
        OptionsFragment fragment = OptionsFragment.newInstance(mSelectedIndex);
        fragment.show(getChildFragmentManager(), OptionsFragment.class.getSimpleName());
    }

    @Override
    public void done(String selectedString) {
        mSelectedIndex = mKeys.indexOf(selectedString);
        updateTitle();
    }

    @OnClick(R.id.fab)
    public void clicked(View v) {
        /**
         * Showing google speech input dialog
         * */
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, mKeys.get(mSelectedIndex));

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Snackbar.make(v, "Speech To Text not supported", Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mTv.setText(result.get(0));
                }
                break;
            }
        }
    }

}
