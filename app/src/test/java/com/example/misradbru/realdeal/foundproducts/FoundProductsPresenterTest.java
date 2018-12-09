package com.example.misradbru.realdeal.foundproducts;

import com.example.misradbru.realdeal.data.FoundProduct;
import com.example.misradbru.realdeal.data.ProductRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class FoundProductsPresenterTest {

    private final String SEARCH_ID = "search_id";
    private final String UID = "123456";
    private final String PRODUCT_LINK = "www.allegro.com/supershoes";

    @Mock
    private FoundProductsContract.View mFoundProductsView;
    @Mock
    private ProductRepository mProductRepository;
    @Mock
    private FoundProductsAdapter mFoundProductsAdapter;

    private FoundProductsPresenter mFoundProductsPresenter;

    @Before
    public void setupAddNotePresenter() {

        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mFoundProductsPresenter = new FoundProductsPresenter(mProductRepository, mFoundProductsView);
    }


    @Test
    public void showFoundProducts() {
        mFoundProductsPresenter.showFoundProducts(SEARCH_ID, mFoundProductsAdapter);

        verify(mFoundProductsAdapter).registerDataSetObserver(mFoundProductsPresenter.dataSetObserver);
        verify(mFoundProductsView).setProgressIndicator(true);
        verify(mProductRepository).getFoundProducts(SEARCH_ID, mFoundProductsAdapter);
    }

    @Test
    public void foundProductClicked() {
        mFoundProductsPresenter.foundProductClicked(
                new FoundProduct("Shoes", PRODUCT_LINK, "330", "PLN", "allegro"));

        verify(mFoundProductsView).openFoundProductPage(PRODUCT_LINK);
    }

    @Test
    public void deleteProduct() {
        mFoundProductsPresenter.deleteProduct(SEARCH_ID);

        verify(mProductRepository).deleteSearch(SEARCH_ID);
        verify(mFoundProductsView).showSearches();
    }
}