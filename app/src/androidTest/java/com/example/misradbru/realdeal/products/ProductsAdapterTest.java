package com.example.misradbru.realdeal.products;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import com.example.misradbru.realdeal.R;
import com.example.misradbru.realdeal.data.Product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class ProductsAdapterTest {

    private ProductsAdapter mProductsAdapter;
    private Product violin;
    private Product book;
    private Product phone;

    @Before
    public void setUp() {
        List<Product> products = new ArrayList<>();
        violin = new Product(
                "Violin for Violet",
                "Strativarius violin",
                1000,
                2000,
                "userID1");
        book = new Product(
                "Great book",
                "Hitchhikers guide to the galaxy",
                100,
                200,
                "userID1");
        phone = new Product(
                "Phone that works",
                "Xiaomi Redmi 3s",
                600,
                800,
                "userID1");

        products.add(violin);
        products.add(book);
        products.add(phone);
        mProductsAdapter = new ProductsAdapter(InstrumentationRegistry.getTargetContext(), products);

    }

    @Test
    public void testGetItemAtPosition() {
        assertEquals("Violin for violet was expected.", violin.getName(),
                (Objects.requireNonNull(mProductsAdapter.getItem(0))).getName());
    }

    @Test
    public void testGetItemId() {
        assertEquals("Wrong ID", 1, mProductsAdapter.getItemId(1));
    }

    public void testGetCount() {
        assertEquals("Products amount is incorrect.", 3, mProductsAdapter.getCount());
    }

    @Test
    public void getViewWith0PositionSetsItemNameInTextView() {
        assertNotNull(mProductsAdapter.getContext());

        View view = mProductsAdapter.getView(0, null, null);

        assertNotNull("View is null.", view);

        TextView titleTextView = view.findViewById(R.id.list_item_name);

        assertNotNull("list_item_name TextView is null", titleTextView);

        assertEquals("Product names don't match.", violin.getName(), titleTextView.getText().toString());
    }

    @Test
    public void getViewWithNon0Position() {
        assertNotNull(mProductsAdapter.getContext());

        View view = mProductsAdapter.getView(2, null, null);

        assertNotNull("View is null.", view);

        TextView titleTextView = view.findViewById(R.id.list_item_name);

        assertNotNull("list_item_name TextView is null", titleTextView);

        assertEquals("Product names don't match.", phone.getName(), titleTextView.getText().toString());
    }
}