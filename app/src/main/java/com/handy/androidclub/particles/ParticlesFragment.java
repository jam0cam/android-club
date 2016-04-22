package com.handy.androidclub.particles;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.handy.androidclub.R;
import com.plattysoft.leonids.ParticleSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jtse on 4/22/16.
 */
public class ParticlesFragment extends Fragment {

    private static final String TAG = ParticlesFragment.class.getName();
    public static final int COLORS = 12;
    private static final int FIREWORKS_COUNT = 25;

    @DrawableRes
    List<Integer> mDrawables;

    List<ParticleSystem> mLeftParticles;
    List<ParticleSystem> mRightParticles;


    @Bind(R.id.left_center_view)
    View mLeftCenterView;

    @Bind(R.id.right_center_view)
    View mRightCenterView;

    @Bind(R.id.middle_view)
    View mCenterView;

    @Bind(R.id.table_layout)
    TableLayout mTableLayout;

    private int mRotationSpeed = 144;
    private int mAccelerationAngle = 90;

    public List<Button> mButtons = new ArrayList<>();
    private Random mRandom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_particles, container, false);

        ButterKnife.bind(this, v);
        mDrawables = new ArrayList<>(COLORS);
        mLeftParticles = new ArrayList<>(COLORS);
        mRightParticles = new ArrayList<>(COLORS);

        mDrawables.add(R.mipmap.confetti_1);
        mDrawables.add(R.mipmap.confetti_2);
        mDrawables.add(R.mipmap.confetti_3);
        mDrawables.add(R.mipmap.confetti_4);
        mDrawables.add(R.mipmap.confetti_5);
        mDrawables.add(R.mipmap.confetti_6);
        mDrawables.add(R.mipmap.confetti_7);
        mDrawables.add(R.mipmap.confetti_8);
        mDrawables.add(R.mipmap.confetti_9);
        mDrawables.add(R.mipmap.confetti_10);
        mDrawables.add(R.mipmap.confetti_11);
        mDrawables.add(R.mipmap.confetti_12);

        mRandom = new Random(System.currentTimeMillis());

        //this is setup for fireworks:
        for (int i=0; i<11; i++) {
            TableRow row = new TableRow(getActivity());
            row.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            row.addView(getNewButton(getActivity()));
            row.addView(getNewButton(getActivity()));
            row.addView(getNewButton(getActivity()));
            mTableLayout.addView(row);
        }

        return v;
    }


    @OnClick(R.id.btn_gravity)
    public void gravityConfetti() {
        int partNumPerSecond = 2;
        int emitTime = 1000;
        float acceleration = 0.000685f;
        for (int i = 0; i < COLORS; i++)
        {
            new ParticleSystem(getActivity(), 5, mDrawables.get(i), 5000)
                    .setSpeedModuleAndAngleRange(0.1f, 0.3f, 225, 315)
                    .setRotationSpeed(mRotationSpeed)
                    .setAcceleration(acceleration, mAccelerationAngle)
                    .setScaleRange(0.3f, 0.5f)
                    .emit(mRightCenterView, partNumPerSecond, emitTime);

            new ParticleSystem(getActivity(), 5, mDrawables.get(i), 5000)
                    .setSpeedModuleAndAngleRange(0.1f, 0.3f, 225, 315)
                    .setRotationSpeed(mRotationSpeed)
                    .setAcceleration(acceleration, mAccelerationAngle)
                    .setScaleRange(0.3f, 0.5f)
                    .emit(mLeftCenterView, partNumPerSecond, emitTime);

            new ParticleSystem(getActivity(), 5, mDrawables.get(i), 5000)
                    .setSpeedModuleAndAngleRange(0.1f, 0.3f, 225, 315)
                    .setRotationSpeed(mRotationSpeed)
                    .setAcceleration(acceleration, mAccelerationAngle)
                    .setScaleRange(0.3f, 0.5f)
                    .emit(mCenterView, partNumPerSecond, emitTime);
        }
    }

    @OnClick(R.id.btn_shooting)
    public void shootingConfetti() {
        int partNumPerSecond = 5;
        int emitTime = 5000;
        int rotationSpeed = 144;
        int accelerationAngle = 90;
        float acceleration = 0.001985f;
        int maxParticles = 500;
        for (int i=0; i<COLORS; i++) {
            new ParticleSystem(getActivity(), maxParticles, mDrawables.get(i), 10000)
                    .setSpeedModuleAndAngleRange(0.1f, 0.3f, 225, 315)
                    .setRotationSpeed(rotationSpeed)
                    .setAcceleration(acceleration, accelerationAngle)
                    .setScaleRange(0.3f, 0.5f)
                    .emit(mRightCenterView, partNumPerSecond, emitTime);

            new ParticleSystem(getActivity(), maxParticles, mDrawables.get(i), 10000)
                    .setSpeedModuleAndAngleRange(0.1f, 0.3f, 225, 315)
                    .setRotationSpeed(rotationSpeed)
                    .setAcceleration(acceleration, accelerationAngle)
                    .setScaleRange(0.3f, 0.5f)
                    .emit(mLeftCenterView, partNumPerSecond, emitTime);

            new ParticleSystem(getActivity(), maxParticles, mDrawables.get(i), 10000)
                    .setSpeedModuleAndAngleRange(0.1f, 0.3f, 225, 315)
                    .setRotationSpeed(rotationSpeed)
                    .setAcceleration(acceleration, accelerationAngle)
                    .setScaleRange(0.3f, 0.5f)
                    .emit(mCenterView, partNumPerSecond, emitTime);
        }
    }

    /**
     * Create a new button and add it to the list so we can have a reference to it.
     */
    private Button getNewButton(Context context) {
        Button button = new Button(context);
        button.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        mButtons.add(button);
        return button;
    }

    private Button getRandomButton() {
        return mButtons.get(mRandom.nextInt(mButtons.size()));
    }

    @OnClick(R.id.btn_fireworks)
    public void fireworks() {
        int totalDelay = 0;
        for (int i=1; i <= FIREWORKS_COUNT; i++) {

            int delay = mRandom.nextInt(1500);
            totalDelay += delay;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Button button = getRandomButton();
                    Integer d = mDrawables.get(mRandom.nextInt(mDrawables.size()));
                    ParticleSystem ps = new ParticleSystem(getActivity(), 100, d, 800);
                    ps.setScaleRange(0.1f, 0.2f);
                    ps.setSpeedRange(0.1f, 0.25f);
                    ps.setRotationSpeedRange(90, 180);
                    ps.setFadeOut(200, new AccelerateInterpolator());
                    ps.oneShot(button, 70);
                }
            }, delay);
        }
    }
}
