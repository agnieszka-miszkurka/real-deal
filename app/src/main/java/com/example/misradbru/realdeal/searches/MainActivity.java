package com.example.misradbru.realdeal.searches;

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
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.misradbru.realdeal.R;
import com.example.misradbru.realdeal.addsearch.AddSearchActivity;
import com.example.misradbru.realdeal.data.SearchProduct;
import com.example.misradbru.realdeal.data.ProductRepositoryImpl;
import com.example.misradbru.realdeal.foundproducts.FoundProductsActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    public static final String ANONYMOUS = "anonymous";
    String mUsername;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private String TAG = "MainActivity";

    private ListView mProductListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProductListView = findViewById(R.id.products_list);

        mProductListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchProduct clickedSearchProduct = (SearchProduct) parent.getAdapter().getItem(position);

                Intent intent = new Intent(getApplicationContext(), FoundProductsActivity.class);
                intent.putExtra(FoundProductsActivity.PRODUCT_NAME, clickedSearchProduct.getName());
                intent.putExtra(FoundProductsActivity.SEARCH_PHRASE, clickedSearchProduct.getSearchPhrase());
                intent.putExtra(FoundProductsActivity.UID, clickedSearchProduct.getUid());
                startActivity(intent);
            }
        });

        authenticate();
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
                    createProductsList();
                } else {
                    onSignedOutCleanUp();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setLogo(R.drawable.icon)
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
                Intent intent =  new Intent(getApplicationContext(), AddSearchActivity.class);
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
        } else {
            Log.d(context, "Email not verified");
            Intent intent = new Intent(this, EmailVerfificationActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .putExtra("EXIT", true);
            startActivity(intent);
        }
    }

    private void createProductsList() {
       ProductRepositoryImpl repository = new ProductRepositoryImpl();
       repository.getProducts(mAuth.getUid(),this,mProductListView);
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
}
