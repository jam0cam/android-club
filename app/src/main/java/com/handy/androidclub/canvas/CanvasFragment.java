package com.handy.androidclub.canvas;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handy.androidclub.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CanvasFragment extends Fragment {

    @Bind(R.id.canvas_view)
    CanvasView mCanvasView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_canvas, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mCanvasView.setPercentage(.8f);
        mCanvasView.setColor(Color.WHITE, 0xFF8BC538, 0xFF8BC538);
    }
}
