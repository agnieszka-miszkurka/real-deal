package com.example.misradbru.realdeal.searches;

import android.database.DataSetObserver;

import com.example.misradbru.realdeal.data.ProductRepository;
import com.example.misradbru.realdeal.data.SearchProduct;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;


public class SearchesPresenterTest {

    private String USER_ID = "userID";

    @Mock
    private ProductRepository mProductRepository;

    @Mock
    private SearchesContract.View mSearchesView;

    @Mock
    private SearchesAdapter mSearchesAdapter;

    @Mock
    private SearchProduct searchProduct;

    @Mock
    private DataSetObserver dataSetObserver;

    private SearchesPresenter mSearchesPresenter;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mSearchesPresenter = new SearchesPresenter(mProductRepository, mSearchesView);
    }

    @Test
    public void loadSearchProducts() {
        mSearchesPresenter.loadSearchProducts(USER_ID, mSearchesAdapter);
        verify(mSearchesAdapter).registerDataSetObserver(mSearchesPresenter.dataSetObserver);
        verify(mSearchesView).setProgressIndicator(true);
        verify(mProductRepository).getSearches(USER_ID, mSearchesAdapter);
    }

    @Test
    public void loadSearchProducts_onDataSetChange() {
        mSearchesPresenter.dataSetObserver.onChanged();

        verify(mSearchesView).setProgressIndicator(false);
    }

    @Test
    public void openFoundProducts() {
        mSearchesPresenter.openFoundProducts(searchProduct);
        verify(mSearchesView).showFoundProductsUi(searchProduct);
    }

    @Test
    public void addNewSearch() {
        mSearchesPresenter.addNewSearch();
        verify(mSearchesView).showAddSearch();
    }
}