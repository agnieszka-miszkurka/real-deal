package com.example.misradbru.realdeal.foundproducts;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import com.example.misradbru.realdeal.R;
import com.example.misradbru.realdeal.data.FoundProduct;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class FoundProductsAdapterTest {

    private FoundProductsAdapter mFoundProductsAdapter;
    private FoundProduct foundShoe1;
    private FoundProduct foundShoe2;
    private FoundProduct foundShoe3;

    @Before
    public void setUp() {
        foundShoe1 = new FoundProduct(
                "Beautiful shoe",
                "www.allgero.pl/beautifulshoe1",
                "200",
                "PLN",
                "allegro"
        );

        foundShoe2 = new FoundProduct(
                "Beautiful shoe2",
                "www.allgero.pl/beautifulshoe2",
                "250",
                "PLN",
                "allegro"
        );

        foundShoe3 = new FoundProduct(
                "Beautiful shoe3",
                "www.allgero.pl/beautifulshoe3",
                "300",
                "PLN",
                "allegro"
        );

        List<FoundProduct> foundProductsList = new ArrayList<>();
        foundProductsList.add(foundShoe1);
        foundProductsList.add(foundShoe2);
        foundProductsList.add(foundShoe3);

        mFoundProductsAdapter = new FoundProductsAdapter(InstrumentationRegistry.getTargetContext(),
                foundProductsList);
    }

    @Test
    public void testGetItemId() {
        assertEquals("Wrong ID", 1, mFoundProductsAdapter.getItemId(1));
    }

    public void testGetCount() {
        assertEquals("Products amount is incorrect.", 3, mFoundProductsAdapter.getCount());
    }

    @Test
    public void getViewWith0PositionSetsItemNameInTextView() {
        assertNotNull(mFoundProductsAdapter.getContext());

        View view = mFoundProductsAdapter.getView(0, null, null);

        assertNotNull("View is null.", view);

        TextView titleTextView = view.findViewById(R.id.found_product_name_tv);
        TextView providerTextView = view.findViewById(R.id.found_product_provider_tv);
        TextView priceTextView = view.findViewById(R.id.price_tv);

        assertNotNull("list_item_name TextView is null", titleTextView);

        assertEquals("FoundProducts names don't match.",
                foundShoe1.getName(), titleTextView.getText().toString());
        assertEquals("FoundProducts names don't match.",
                foundShoe1.getProvider(), providerTextView.getText().toString());
        assertEquals("FoundProducts names don't match.",
                foundShoe1.getPrice() + " " + foundShoe1.getCurrency(), priceTextView.getText().toString());
    }

    @Test
    public void getViewWithNon0Position() {
        assertNotNull(mFoundProductsAdapter.getContext());

        View view = mFoundProductsAdapter.getView(2, null, null);

        assertNotNull("View is null.", view);

        TextView titleTextView = view.findViewById(R.id.found_product_name_tv);
        TextView providerTextView = view.findViewById(R.id.found_product_provider_tv);
        TextView priceTextView = view.findViewById(R.id.price_tv);

        assertNotNull("list_item_name TextView is null", titleTextView);

        assertEquals("SearchProduct names don't match.",
                foundShoe3.getName(), titleTextView.getText().toString());
        assertEquals("FoundProducts names don't match.",
                foundShoe3.getProvider(), providerTextView.getText().toString());
        assertEquals("FoundProducts names don't match.",
                foundShoe3.getPrice() + " " + foundShoe3.getCurrency(), priceTextView.getText().toString());
    }
}