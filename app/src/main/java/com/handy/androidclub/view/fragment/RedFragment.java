package com.handy.androidclub.view.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.ColorUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.handy.androidclub.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RedFragment extends Fragment {

    public static final String TAG = "RedFragment";

    @Bind(R.id.big_text_text)
    TextView mTextView;
    @Bind(R.id.big_text_frame)
    FrameLayout mFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        final View root = inflater.inflate(R.layout.fragment_big_text, container, false);
        ButterKnife.bind(this, root);
        mTextView.setTextColor(Color.WHITE);
        mTextView.setText("R E D");
        mFrameLayout.setBackgroundColor(Color.RED);
        return root;
    }

}
