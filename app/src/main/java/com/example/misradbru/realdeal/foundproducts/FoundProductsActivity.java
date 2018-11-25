package com.example.misradbru.realdeal.foundproducts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.misradbru.realdeal.R;

import java.util.Objects;

public class FoundProductsActivity extends AppCompatActivity {

    public static final String PRODUCT_NAME = "PRODUCT_NAME";
    public static final String SEARCH_PRODUCT_ID = "SEARCH_PRODUCT_ID";
    public static final String UID = "UID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_products);

        String productName = getIntent().getStringExtra(PRODUCT_NAME);
        String searchProductId = getIntent().getStringExtra(SEARCH_PRODUCT_ID);
        String uid = getIntent().getStringExtra(UID);

        Objects.requireNonNull(getSupportActionBar()).setTitle(productName);

        initFragment(FoundProductsFragment.newInstance(searchProductId, uid));

    }

    private void initFragment(Fragment detailFragment) {
        // Add the NotesDetailFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame, detailFragment);
        transaction.commit();
    }
}
