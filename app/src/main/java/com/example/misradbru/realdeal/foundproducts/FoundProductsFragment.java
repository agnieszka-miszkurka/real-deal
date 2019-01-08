package com.example.misradbru.realdeal.foundproducts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.misradbru.realdeal.R;
import com.example.misradbru.realdeal.data.FoundProduct;
import com.example.misradbru.realdeal.data.ProductRepositoryImpl;

import java.util.ArrayList;
import java.util.Objects;

public class FoundProductsFragment extends Fragment implements FoundProductsContract.View {

    String mSearchId;
    String mUid;
    private ProgressBar mProgressBar;
    private Button mButton;
    ListView mFoundProductsList;

    FoundProductsContract.UserActionsListener mActionsListener;

    public FoundProductsFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(String searchId, String uid) {
        FoundProductsFragment fragment = new FoundProductsFragment();
        Bundle args = new Bundle();
        args.putString(FoundProductsActivity.SEARCH_ID, searchId);
        args.putString(FoundProductsActivity.UID, uid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSearchId = getArguments().getString(FoundProductsActivity.SEARCH_ID);
            mUid = getArguments().getString(FoundProductsActivity.UID);
        }

        mActionsListener = new FoundProductsPresenter(new ProductRepositoryImpl(), this);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_found_products, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressBar = view.findViewById(R.id.found_products_progressbar);
        mButton = view.findViewById(R.id.delete_btn);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionsListener.deleteProduct(mSearchId);
            }
        });

        mFoundProductsList = view.findViewById(R.id.found_products_listview);
        mFoundProductsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoundProduct clickedFoundProduct = (FoundProduct) parent.getAdapter().getItem(position);
                mActionsListener.foundProductClicked(clickedFoundProduct);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        FoundProductsAdapter foundProductsAdapter =
                new FoundProductsAdapter(getContext(), new ArrayList<FoundProduct>());
        mFoundProductsList.setAdapter(foundProductsAdapter);
        mActionsListener.showFoundProducts(mSearchId, foundProductsAdapter);
    }

    @Override
    public void setProgressIndicator(boolean active) {
        if (active) {
            mProgressBar.setVisibility(View.VISIBLE);
            mButton.setVisibility(View.GONE);
            mFoundProductsList.setVisibility(View.GONE);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mButton.setVisibility(View.VISIBLE);
            mFoundProductsList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void openFoundProductPage(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    public void showSearches() {
        Objects.requireNonNull(getActivity()).finish();
    }
}
