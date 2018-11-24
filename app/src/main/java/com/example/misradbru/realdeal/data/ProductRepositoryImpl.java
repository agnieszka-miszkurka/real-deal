package com.example.misradbru.realdeal.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.misradbru.realdeal.foundproducts.FoundProductsAdapter;
import com.example.misradbru.realdeal.searches.SearchesAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductRepositoryImpl implements ProductRepository {
    private final String TAG = "ProductRepositoryImpl";
    private final String SEARCHES_COLLECTION = "searches";
    private final String FOUND_PRODUCTS_COLLECTION = "foundProducts";
    private FirebaseFirestore db;

    public ProductRepositoryImpl() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }

    @Override
    public void saveSearchProduct(@NonNull SearchProduct searchProduct) {
        db.collection(SEARCHES_COLLECTION)
                .add(searchProduct)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    @Override
    public void getSearches(String userId, final SearchesAdapter mSearchesAdapter) {
        final List<SearchProduct> mSearchProductList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();  // TODO: check if it's necessary
        db.collection(SEARCHES_COLLECTION)
                .whereEqualTo("uid", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())){
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                SearchProduct searchProduct = document.toObject(SearchProduct.class);
                                mSearchProductList.add(searchProduct);
                            }
                            mSearchesAdapter.addAll(mSearchProductList);
                            mSearchesAdapter.notifyDataSetChanged();
                        }
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void getFoundProducts(String searchPhrase, final FoundProductsAdapter foundProductsAdapter) {

        final List<FoundProduct> mProductList = new ArrayList<>();

        db.collection(FOUND_PRODUCTS_COLLECTION)
                .whereEqualTo("searchPhrase", searchPhrase)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())){
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                // TODO: change once database is correct
                                mProductList.add(new FoundProduct("KUBEK", "link", "10", "allegro"));
                                //SearchProduct product = document.toObject(SearchProduct.class);
                                //mProductList.add(product);
                            }
                            //mProductListView.setAdapter(mProductsAdapter);
                            foundProductsAdapter.addAll(mProductList);
                            foundProductsAdapter.notifyDataSetChanged();
                        }
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
