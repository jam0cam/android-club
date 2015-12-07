package com.handy.androidclub.view.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.handy.androidclub.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GreenFragment extends Fragment {

    public static final String TAG = "GreenFragment";

    @Bind(R.id.big_text_text)
    TextView mTextView;
    @Bind(R.id.big_text_frame)
    FrameLayout mFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        final View root = inflater.inflate(R.layout.fragment_big_text, container, false);
        ButterKnife.bind(this, root);
        mTextView.setTextColor(Color.WHITE);
        mTextView.setText("G R E E N");
        mFrameLayout.setBackgroundColor(Color.GREEN);
        return root;
    }

}
