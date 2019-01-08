package com.example.misradbru.realdeal.addsearch;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.misradbru.realdeal.R;
import com.example.misradbru.realdeal.data.ProductRepositoryImpl;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSearchFragment extends Fragment implements AddSearchContract.View {

    private AddSearchContract.UserActionsListener mActionListener;

    private TextView mProductNameTextView;
    private TextView mSearchPhraseTextView;
    private TextView mMinPriceTextView;
    private TextView mMaxPriceTextView;

    private String userID;

    public AddSearchFragment() {
        // Required empty public constructor
    }

    public static AddSearchFragment newInstance() {
        return new AddSearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_search, container, false);

        mProductNameTextView = root.findViewById(R.id.productName);
        mSearchPhraseTextView = root.findViewById(R.id.searchPhrase);
        mMinPriceTextView = root.findViewById(R.id.minPrice);
        mMaxPriceTextView = root.findViewById(R.id.maxPrice);

        Button addButton = root.findViewById(R.id.addNewProductBtn);

        mActionListener = new AddSearchPresenter(this, new ProductRepositoryImpl());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            userID = bundle.getString("UID_STRING", "0");
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionListener.saveProduct(
                        mProductNameTextView.getText().toString(),
                        mSearchPhraseTextView.getText().toString(),
                        mMinPriceTextView.getText().toString(),
                        mMaxPriceTextView.getText().toString(),
                        userID
                );
            }
        });

        return root;
    }


    @Override
    public void showEmptyProductError() {
        Snackbar.make(mProductNameTextView,
                getString(R.string.empty_addproduct_message), Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void showMinMaxPriceMismatch() {
        Snackbar.make(mMinPriceTextView,
                getString(R.string.pricemismatch_addproduct_message), Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void showProductList() {
        Activity activity = getActivity();
        assert activity != null;
        activity.setResult(Activity.RESULT_OK);
        activity.finish();
    }
}
