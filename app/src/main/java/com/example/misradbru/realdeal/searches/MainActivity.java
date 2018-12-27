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
import android.widget.ProgressBar;

import com.example.misradbru.realdeal.R;
import com.example.misradbru.realdeal.addsearch.AddSearchActivity;
import com.example.misradbru.realdeal.data.ProductRepositoryImpl;
import com.example.misradbru.realdeal.data.SearchProduct;
import com.example.misradbru.realdeal.data.TokenRepository;
import com.example.misradbru.realdeal.data.TokenRepositoryImpl;
import com.example.misradbru.realdeal.foundproducts.FoundProductsActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements SearchesContract.View{

    private static final int RC_SIGN_IN = 123;
    public static final String ANONYMOUS = "anonymous";
    String mUsername;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private SearchesContract.UserActionListener mActionsListener;
    private ProgressBar mProgressBar;

    private String TAG = "MainActivity";

    private ListView mProductListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProductListView = findViewById(R.id.products_list);
        mProgressBar = findViewById(R.id.searches_progressbar);
        mActionsListener = new SearchesPresenter(new ProductRepositoryImpl(), this);

        mProductListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchProduct clickedSearchProduct = (SearchProduct) parent.getAdapter().getItem(position);

                mActionsListener.openFoundProducts(clickedSearchProduct);
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
                    //createProductsList();
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
                mActionsListener.addNewSearch();
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

                assert user != null;
                updateUidForToken(user.getUid());

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

    void updateUidForToken(final String uid) {

        final TokenRepository tokenRepository = new TokenRepositoryImpl();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        tokenRepository.updateUid(uid, token);
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
        SearchesAdapter mSearchesAdapter =
                new SearchesAdapter(this.getApplicationContext(), new ArrayList<SearchProduct>());
        mProductListView.setAdapter(mSearchesAdapter);
        mActionsListener.loadSearchProducts(mAuth.getUid(), mSearchesAdapter);
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    public void setProgressIndicator(boolean active) {
        if (active) {
            mProgressBar.setVisibility(View.VISIBLE);
            mProductListView.setVisibility(View.GONE);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mProductListView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showAddSearch() {
        Intent intent =  new Intent(getApplicationContext(), AddSearchActivity.class);
        intent.putExtra(AddSearchActivity.UID_STRING, mAuth.getUid());
        startActivity(intent);
    }

    @Override
    public void showFoundProductsUi(SearchProduct searchProduct) {
        Intent intent = new Intent(getApplicationContext(), FoundProductsActivity.class);
        intent.putExtra(FoundProductsActivity.PRODUCT_NAME, searchProduct.getName());
        intent.putExtra(FoundProductsActivity.SEARCH_ID, searchProduct.getSearchId());
        intent.putExtra(FoundProductsActivity.UID, searchProduct.getUid());
        startActivity(intent);
    }
}
