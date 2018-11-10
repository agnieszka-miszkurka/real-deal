package com.example.misradbru.realdeal.addproduct;

import com.example.misradbru.realdeal.data.Product;
import com.example.misradbru.realdeal.data.ProductRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class AddProductPresenterTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AddProductContract.View mAddProductView;

    private AddProductPresenter mAddProductPresenter;

    @Before
    public void setupAddNotePresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mAddProductPresenter = new AddProductPresenter(mAddProductView, productRepository);
    }

    @Test
    public void saveProduct_emptyFieldShowsErrorUi() {
        // When the presenter is asked to save an empty product
        mAddProductPresenter.saveProduct("","", "", "", "UID");

        // Then an empty product message is displayed in the UI
        verify(mAddProductView).showEmptyProductError();
    }

    @Test
    public void saveProduct_priceMismatchShowsErrorUi() {
        // When the presenter is asked to save an empty product
        mAddProductPresenter.saveProduct("Bike","Bike Giant", "33", "10", "UID");

        // Then an empty product message is displayed in the UI
        verify(mAddProductView).showMinMaxPriceMismatch();
    }

    @Test
    public void saveNoteToRepository_showsSuccessMessageUi() {
        // When the presenter is asked to save a product
        mAddProductPresenter.saveProduct("Bike", "Bike Giant", "20", "30", "UID");

        // Then a product is,
        verify(productRepository).saveProduct(any(Product.class)); // saved to the model
        verify(mAddProductView).showProductList(); // shown in the UI
    }
}