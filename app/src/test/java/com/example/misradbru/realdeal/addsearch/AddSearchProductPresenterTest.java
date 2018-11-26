package com.example.misradbru.realdeal.addsearch;

import com.example.misradbru.realdeal.data.SearchProduct;
import com.example.misradbru.realdeal.data.ProductRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class AddSearchProductPresenterTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AddSearchContract.View mAddProductView;

    private AddSearchPresenter mAddSearchPresenter;

    @Before
    public void setupAddNotePresenter() {

        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mAddSearchPresenter = new AddSearchPresenter(mAddProductView, productRepository);
    }

    @Test
    public void saveProduct_emptyFieldShowsErrorUi() {
        // When the presenter is asked to save an empty product
        mAddSearchPresenter.saveProduct("","", "", "", "UID");

        // Then an empty product message is displayed in the UI
        verify(mAddProductView).showEmptyProductError();
    }

    @Test
    public void saveProduct_priceMismatchShowsErrorUi() {
        // When the presenter is asked to save an empty product
        mAddSearchPresenter.saveProduct("Bike","Bike Giant", "33", "10", "UID");

        // Then an empty product message is displayed in the UI
        verify(mAddProductView).showMinMaxPriceMismatch();
    }

    @Test
    public void saveNoteToRepository_showsSuccessMessageUi() {
        // When the presenter is asked to save a product
        mAddSearchPresenter.saveProduct("Bike", "Bike Giant", "20", "30", "UID");

        // Then a product is,
        verify(productRepository).saveSearchProduct(any(SearchProduct.class)); // saved to the model
        verify(mAddProductView).showProductList(); // shown in the UI
    }
}