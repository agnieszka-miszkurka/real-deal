package com.example.misradbru.realdeal.addsearch;

import com.example.misradbru.realdeal.data.SearchProduct;
import com.example.misradbru.realdeal.data.ProductRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class AddSearchPresenterTest {

    private static final String accents 	= "Ą,ą,Ć,ć,Ż,ż,Ł,ł,Ó,ó,Ę,ę,Ś,ś,Ź,ź,Ń,ń";
    private static final String expected	= "A,a,C,c,Z,z,L,l,O,o,E,e,S,s,Z,z,N,n";

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
        mAddSearchPresenter.saveProduct("","", "", "", "UID_STRING");

        // Then an empty product message is displayed in the UI
        verify(mAddProductView).showEmptyProductError();
    }

    @Test
    public void saveProduct_priceMismatchShowsErrorUi() {
        // When the presenter is asked to save an empty product
        mAddSearchPresenter.saveProduct("Bike","Bike Giant", "33", "10", "UID_STRING");

        // Then an empty product message is displayed in the UI
        verify(mAddProductView).showMinMaxPriceMismatch();
    }

    @Test
    public void saveProductToRepository_showsSuccessMessageUi() {
        // When the presenter is asked to save a product
        mAddSearchPresenter.saveProduct("Bike", "Bike Giant", "20", "30", "UID_STRING");

        // Then a product is,
        verify(productRepository).saveSearchProduct(any(SearchProduct.class)); // saved to the model
        verify(mAddProductView).showProductList(); // shown in the UI
    }

    @Test
    public void unaccent_changesPolishLettersCorrectly() {
        assertThat(mAddSearchPresenter.unaccent(accents)).isEqualTo(expected);
    }
}