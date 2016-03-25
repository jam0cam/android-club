package com.handy.androidclub.canvas;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handy.androidclub.R;

public class CanvasFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_canvas, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        CanvasView canvasView = (CanvasView) view.findViewById(R.id.canvas_view);
        canvasView.setPercentage(.8f);
        canvasView.setColor(Color.WHITE, 0xFF8BC538, 0xFF8BC538);
    }
}
