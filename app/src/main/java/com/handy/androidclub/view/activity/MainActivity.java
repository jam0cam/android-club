package com.handy.androidclub.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.analytics.HitBuilders;
import com.handy.androidclub.R;
import com.handy.androidclub.core.App;
import com.handy.androidclub.view.fragment.GreenFragment;
import com.handy.androidclub.view.fragment.HomeFragment;
import com.handy.androidclub.view.fragment.RedFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager mFragmentManager = getSupportFragmentManager();
    int mPreviousSelectedMenuItem = 0;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(
                R.id.fragment_container,
                HomeFragment.newInstance("Welcome Home"),
                HomeFragment.TAG
        );
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (mFragmentManager.getBackStackEntryCount() > 0) {
                String title = mFragmentManager
                        .getBackStackEntryAt(mFragmentManager.getBackStackEntryCount() - 1)
                        .getName();
                int pos = Integer.parseInt(title);
                mPreviousSelectedMenuItem = pos;
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            App.tracker().send(new HitBuilders.EventBuilder("ui", "open")
                    .setLabel("settings")
                    .build());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation drawer item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_home:
                swapFragment(HomeFragment.newInstance("Welcome"));
                mPreviousSelectedMenuItem = item.getItemId();
                break;
            case R.id.nav_one:
                swapFragment(new RedFragment());
                mPreviousSelectedMenuItem = item.getItemId();
                break;
            case R.id.nav_two:
                swapFragment(new GreenFragment());
                mPreviousSelectedMenuItem = item.getItemId();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
