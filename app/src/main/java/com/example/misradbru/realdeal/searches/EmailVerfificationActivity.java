package com.example.misradbru.realdeal.searches;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.misradbru.realdeal.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class EmailVerfificationActivity extends AppCompatActivity {

    @NonNull
    FirebaseAuth setFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verfification);

        final FirebaseAuth mAuth = setFirebaseAuth();
        final FirebaseUser user = mAuth.getCurrentUser();

        Button sentEmailButton = findViewById(R.id.send_email_btn);
        sentEmailButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                assert user != null;
                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        findViewById(R.id.send_email_btn).setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(EmailVerfificationActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("EmailVerivication", "sendEmailVerification", task.getException());
                            Toast.makeText(EmailVerfificationActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        Button refreshButton = findViewById(R.id.refresh_btn);
        assert user != null;

        refreshButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Task<Void> usertask = Objects.requireNonNull(mAuth.getCurrentUser()).reload();
                usertask.addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        FirebaseUser nUser = mAuth.getCurrentUser();
                        if (nUser.isEmailVerified()) {
                            finish();
                        }
                    }

                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        AuthUI.getInstance().signOut(this);
        finish();
    }
}
