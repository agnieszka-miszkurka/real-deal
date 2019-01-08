package com.example.misradbru.realdeal.addsearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.misradbru.realdeal.R;

public class AddSearchActivity extends AppCompatActivity {

    String uid;
    public static final String UID_STRING = "UID_STRING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_search);

        // Set up the toolbar.
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.add_product);
        uid = getIntent().getStringExtra(UID_STRING);

        initFragment(AddSearchFragment.newInstance());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void initFragment(Fragment detailFragment) {
        // Add the AddSearchFragment to the layout
        Bundle bundle = new Bundle();
        bundle.putString(UID_STRING, uid);
        detailFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame, detailFragment);
        transaction.commit();
    }

}
