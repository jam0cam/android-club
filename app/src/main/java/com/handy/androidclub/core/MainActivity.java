package com.handy.androidclub.core;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.analytics.HitBuilders;
import com.handy.androidclub.R;
import com.handy.androidclub.camera.CameraFragment;
import com.handy.androidclub.canvas.CanvasFragment;
import com.handy.androidclub.chat.ChatFragment;
import com.handy.androidclub.particles.ParticlesFragment;
import com.handy.androidclub.places.AddressFragment;
import com.handy.androidclub.speech.SpeechFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private int mPreviousSelectedMenuItem = 0;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                mDrawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            if (mFragmentManager.getBackStackEntryCount() > 0) {
                String title = mFragmentManager
                        .getBackStackEntryAt(mFragmentManager.getBackStackEntryCount() - 1)
                        .getName();
                int pos = Integer.parseInt(title);
                mNavigationView.setCheckedItem(pos);
                super.onBackPressed();
            } else {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Quit")
                        .setMessage("Are you sure you want to leave ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                App.tracker().send(new HitBuilders.EventBuilder("ui", "open")
                        .setLabel("settings")
                        .build());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                swapFragment(HomeFragment.newInstance("Welcome"));
                mPreviousSelectedMenuItem = item.getItemId();
                break;
            case R.id.nav_canvas:
                swapFragment(new CanvasFragment());
                mPreviousSelectedMenuItem = item.getItemId();
                break;
            case R.id.nav_chat:
                swapFragment(new ChatFragment());
                mPreviousSelectedMenuItem = item.getItemId();
                break;
            case R.id.nav_camera:
                swapFragment(new CameraFragment());
                mPreviousSelectedMenuItem = item.getItemId();
                break;
            case R.id.nav_speech:
                swapFragment(new SpeechFragment());
                mPreviousSelectedMenuItem = item.getItemId();
                break;
            case R.id.nav_particles:
                swapFragment(new ParticlesFragment());
                mPreviousSelectedMenuItem = item.getItemId();
                break;
            case R.id.nav_autocomplete:
                swapFragment(new AddressFragment());
                mPreviousSelectedMenuItem = item.getItemId();
                break;
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void swapFragment(final Fragment newFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        android.support.design.R.anim.abc_slide_in_bottom,
                        android.support.design.R.anim.abc_slide_out_top,
                        android.support.design.R.anim.abc_slide_in_top,
                        android.support.design.R.anim.abc_slide_out_bottom
                )
                .replace(R.id.fragment_container, newFragment)
                .addToBackStack(Integer.toString(mPreviousSelectedMenuItem))
                .commit();
    }
}
