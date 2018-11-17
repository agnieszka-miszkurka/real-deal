package com.example.misradbru.realdeal.products;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.misradbru.realdeal.R;
import com.example.misradbru.realdeal.addproduct.AddProductActivity;
import com.example.misradbru.realdeal.data.Product;
import com.example.misradbru.realdeal.data.ProductRepository;
import com.example.misradbru.realdeal.data.ProductRepositoryImpl;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    public static final String ANONYMOUS = "anonymous";
    String mUsername;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private ProductRepository productRepository;

    private String TAG = "MainActivity";
    private ArrayList<String> itemNames = new ArrayList<>();

    private ListView mProductListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProductListView = findViewById(R.id.products_list);
        productRepository = new ProductRepositoryImpl();

        authenticate();

       // prepareItems();
       // initRecyclerView();
    }

    private void authenticate() {
        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    checkIfEmailVerified(user, "ON_CREATE");
                    onSignedInInitialize(user.getDisplayName());
                } else {
                    onSignedOutCleanUp();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: change to MVP model
                Intent intent =  new Intent(getApplicationContext(), AddProductActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Log.d("SING_IN_ON_ACTIVITY", "Sign in successful");
                FirebaseUser user = mAuth.getCurrentUser();
                checkIfEmailVerified(user, "ON_ACTIVITY_RESULT");

            } else if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }

    void checkIfEmailVerified(FirebaseUser user, String context) {
        if (user.isEmailVerified()) {
            Log.d(context, "Email verified");
            createProductsList();
        } else {
            Log.d(context, "Email not verified");
            Intent intent = new Intent(this, EmailVerfificationActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .putExtra("EXIT", true);
            startActivity(intent);
        }
    }

    private void createProductsList() {

        final List<Product> mProductList = new ArrayList<>();
        final ProductsAdapter mProductsAdapter = new ProductsAdapter(this, mProductList);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("products")
                .whereEqualTo("uid", mAuth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){

                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Product product = document.toObject(Product.class);
                                mProductList.add(product);
                            }
                            mProductListView.setAdapter(mProductsAdapter);

                        }
                        else {
                            Log.d("MissionActivity", "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void onSignedOutCleanUp() {
        mUsername = ANONYMOUS;
    }

    void onSignedInInitialize(String username) {
        mUsername = username;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAuth.removeAuthStateListener(mAuthStateListener);
    }

//    private void prepareItems() {
//        Log.d(TAG, "prepareItems: started");
//        SearchListItemsProvider provider = new SearchListItemsProvider();
//        itemNames = provider.provideItems();
//    }

//    public void initRecyclerView() {
//        Log.d(TAG, "initRecyclerView: started");
//        RecyclerView recyclerView = findViewById(R.id.recycler_view);
//        SearchListRecyclerViewAdapter adapter = new SearchListRecyclerViewAdapter(this, itemNames);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//    }
}
