package com.handy.androidclub.canvas;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.handy.androidclub.R;

public class CanvasView extends View {
    private static final float ONE_PERCENT = .01f;

    private Paint mPaintCircle = new Paint();
    private Paint mPaintNumber = new Paint();
    private Paint mPaintBackground = new Paint();
    private RectF mOuterBox = new RectF();
    private RectF mInnerBox = new RectF();
    private boolean mInitialized = false;
    private float mCurrentPercentage = 0;
    private float mPercentage = .8f;
    private int mSize = 0;

    public CanvasView(Context context) {
        super(context);
        init();
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPaintCircle.setColor(Color.GREEN);
        mPaintNumber.setColor(Color.GREEN);
        mPaintBackground.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        if (!mInitialized) {
            initialize();
            mInitialized = true;
        }

        canvas.drawArc(mOuterBox, -90, mCurrentPercentage * 360, true, mPaintCircle);
        canvas.drawArc(mInnerBox, -90, 360, true, mPaintBackground);

        int xPos = (mSize / 2);
        int yPos = (int) ((mSize / 2) - ((mPaintNumber.descent() + mPaintNumber.ascent()) / 2));
        //((mPaintCircle.descent() + mPaintCircle.ascent()) / 2) is the distance from the baseline to the center.

        canvas.drawText(Integer.toString((int) (mCurrentPercentage * 100)), xPos, yPos, mPaintNumber);

        if (mCurrentPercentage < mPercentage) {
            mCurrentPercentage += ONE_PERCENT;
            invalidate();
        }
    }

    public void setPercentage(float percentage) {
        mPercentage = percentage;
    }

    public void setColor(int backgroundColor, int circleColor, int numberColor) {
        mPaintCircle.setColor(circleColor);
        mPaintBackground.setColor(backgroundColor);
        mPaintNumber.setColor(numberColor);
    }

    private void initialize() {
        mSize = Math.min(getWidth(), getHeight());
        float stroke = getResources().getDimension(R.dimen.stroke);

        mPaintNumber.setTextSize(mSize / 3);
        mPaintNumber.setTextAlign(Paint.Align.CENTER);

        mOuterBox = new RectF(0, 0, mSize, mSize);
        mInnerBox = new RectF(stroke, stroke, mSize - stroke, mSize - stroke);
    }

}
