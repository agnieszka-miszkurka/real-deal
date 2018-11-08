package com.example.misradbru.realdeal.addproduct;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProductFragment extends Fragment implements AddProductContract.View {

    private AddProductContract.UserActionsListener mActionListener;

    private TextView ProductNameTextView;
    private TextView SearchPhraseTextView;
    private TextView MinPriceTextView;
    private TextView MaxPriceTextView;

    private Button AddButton;

    public AddProductFragment() {
        // Required empty public constructor
    }

    public static AddProductFragment newInstance() {
        return new AddProductFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_product, container, false);

        ProductNameTextView = root.findViewById(R.id.productName);
        SearchPhraseTextView = root.findViewById(R.id.searchPhrase);
        MinPriceTextView = root.findViewById(R.id.minPrice);
        MaxPriceTextView = root.findViewById(R.id.maxPrice);

        AddButton = root.findViewById(R.id.addNewProductBtn);

        mActionListener = new AddProductPresenter();

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionListener.saveProduct(
                        ProductNameTextView.getText().toString(),
                        SearchPhraseTextView.getText().toString(),
                        MinPriceTextView.getText().toString(),
                        MaxPriceTextView.getText().toString()
                );
            }
        });

        return root;
    }


    @Override
    public void showEmptyNoteError() {
        Snackbar.make(ProductNameTextView,
                getString(R.string.empty_addproduct_message), Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void showNotesList() {
        Activity activity = getActivity();
        assert activity != null;
        activity.setResult(Activity.RESULT_OK);
        activity.finish();
    }
}
