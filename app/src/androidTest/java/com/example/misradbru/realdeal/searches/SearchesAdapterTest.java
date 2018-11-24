package com.example.misradbru.realdeal.searches;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import com.example.misradbru.realdeal.R;
import com.example.misradbru.realdeal.data.SearchProduct;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class SearchesAdapterTest {

    private SearchesAdapter mSearchesAdapter;
    private SearchProduct violin;
    private SearchProduct book;
    private SearchProduct phone;

    @Before
    public void setUp() {
        List<SearchProduct> searchProducts = new ArrayList<>();
        violin = new SearchProduct(
                "Violin for Violet",
                "Strativarius violin",
                1000,
                2000,
                "userID1");
        book = new SearchProduct(
                "Great book",
                "Hitchhikers guide to the galaxy",
                100,
                200,
                "userID1");
        phone = new SearchProduct(
                "Phone that works",
                "Xiaomi Redmi 3s",
                600,
                800,
                "userID1");

        searchProducts.add(violin);
        searchProducts.add(book);
        searchProducts.add(phone);
        mSearchesAdapter = new SearchesAdapter(InstrumentationRegistry.getTargetContext(), searchProducts);

    }

    @Test
    public void testGetItemAtPosition() {
        assertEquals("Violin for violet was expected.", violin.getName(),
                (Objects.requireNonNull(mSearchesAdapter.getItem(0))).getName());
    }

    @Test
    public void testGetItemId() {
        assertEquals("Wrong ID", 1, mSearchesAdapter.getItemId(1));
    }

    public void testGetCount() {
        assertEquals("Products amount is incorrect.", 3, mSearchesAdapter.getCount());
    }

    @Test
    public void getViewWith0PositionSetsItemNameInTextView() {
        assertNotNull(mSearchesAdapter.getContext());

        View view = mSearchesAdapter.getView(0, null, null);

        assertNotNull("View is null.", view);

        TextView titleTextView = view.findViewById(R.id.list_item_name);

        assertNotNull("list_item_name TextView is null", titleTextView);

        assertEquals("SearchProduct names don't match.", violin.getName(), titleTextView.getText().toString());
    }

    @Test
    public void getViewWithNon0Position() {
        assertNotNull(mSearchesAdapter.getContext());

        View view = mSearchesAdapter.getView(2, null, null);

        assertNotNull("View is null.", view);

        TextView titleTextView = view.findViewById(R.id.list_item_name);

        assertNotNull("list_item_name TextView is null", titleTextView);

        assertEquals("SearchProduct names don't match.", phone.getName(), titleTextView.getText().toString());
    }
}