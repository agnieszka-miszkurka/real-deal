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

    private TextView ProductNameTextView;
    private TextView SearchPhraseTextView;
    private TextView MinPriceTextView;
    private TextView MaxPriceTextView;

    private String userID;

    private Button AddButton;

    public AddSearchFragment() {
        // Required empty public constructor
    }

    public static AddSearchFragment newInstance() {
        return new AddSearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_search, container, false);

        ProductNameTextView = root.findViewById(R.id.productName);
        SearchPhraseTextView = root.findViewById(R.id.searchPhrase);
        MinPriceTextView = root.findViewById(R.id.minPrice);
        MaxPriceTextView = root.findViewById(R.id.maxPrice);

        AddButton = root.findViewById(R.id.addNewProductBtn);

        mActionListener = new AddSearchPresenter(this, new ProductRepositoryImpl());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            userID = bundle.getString("UID", "0");
        }

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionListener.saveProduct(
                        ProductNameTextView.getText().toString(),
                        SearchPhraseTextView.getText().toString(),
                        MinPriceTextView.getText().toString(),
                        MaxPriceTextView.getText().toString(),
                        userID
                );
            }
        });

        return root;
    }


    @Override
    public void showEmptyProductError() {
        Snackbar.make(ProductNameTextView,
                getString(R.string.empty_addproduct_message), Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void showMinMaxPriceMismatch() {
        Snackbar.make(MinPriceTextView,
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
