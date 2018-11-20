package com.example.misradbru.realdeal.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ListView;

import com.example.misradbru.realdeal.foundproducts.FoundProductsAdapter;
import com.example.misradbru.realdeal.products.ProductsAdapter;
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
    private final String PRODUCTS_COLLECTION = "products";
    private FirebaseFirestore db;

    public ProductRepositoryImpl() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }

    @Override
    public void saveProduct(@NonNull Product product) {
        db.collection(PRODUCTS_COLLECTION)
                .add(product)
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
    public void getProducts(String userId, Context context, final ListView mProductListView) {
        final List<Product> mProductList = new ArrayList<>();
        final ProductsAdapter mProductsAdapter = new ProductsAdapter(context, mProductList);
        FirebaseFirestore db = FirebaseFirestore.getInstance();  // TODO: check if it's necessary
        db.collection("products")
                .whereEqualTo("uid", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())){
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Product product = document.toObject(Product.class);
                                mProductList.add(product);
                            }
                            mProductListView.setAdapter(mProductsAdapter);
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

        db.collection("foundProducts")
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
                                //Product product = document.toObject(Product.class);
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
