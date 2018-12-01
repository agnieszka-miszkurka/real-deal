package com.example.misradbru.realdeal.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class TokenRepositoryImpl implements TokenRepository {

    private FirebaseFirestore db;
    private final String TOKENS_COLLECTION = "tokens";
    private final String TAG = "TokenRepository";

    public TokenRepositoryImpl() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }

    /**
     * Check if given uid already exist in the database. If it does - update token for this user.
     * Otherwise create new entry for this user and token.
     * @param uid - user ID
     * @param token - token for FCM
     */
    @Override
    public void addTokenOnTokenChanged(final String uid, final String token) {

        db.collection(TOKENS_COLLECTION)
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            Log.d(TAG, "empty query snapshot");
                            addNewUserAndToken(uid, token);
                        } else {
                            updateToken(uid, token);
                        }
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
    public void updateUid(final String uid, String token) {
        db.collection(TOKENS_COLLECTION)
                .whereEqualTo("token", token)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())){
                            db.collection(TOKENS_COLLECTION)
                                    .document(document.getId())
                                    .update("uid", uid);
                            Log.d(TAG, "DocumentSnapshot updated: " + document.getId());
                        }
                    }
                });
    }

    @Override
    public void updateToken(String uid, final String token) {

        db.collection(TOKENS_COLLECTION)
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())){
                            db.collection(TOKENS_COLLECTION)
                                    .document(document.getId())
                                    .update("token", token);
                            Log.d(TAG, "DocumentSnapshot updated: " + document.getId());
                        }
                    }
                });
    }

    @Override
    public void addNewUserAndToken(String uid, String token) {

        final UserToken userToken = new UserToken(uid, token);

        db.collection(TOKENS_COLLECTION)
                .add(userToken)
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


}
