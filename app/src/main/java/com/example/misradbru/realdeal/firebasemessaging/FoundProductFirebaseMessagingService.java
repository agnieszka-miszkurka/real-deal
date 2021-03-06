package com.example.misradbru.realdeal.firebasemessaging;

import android.util.Log;

import com.example.misradbru.realdeal.data.TokenRepository;
import com.example.misradbru.realdeal.data.TokenRepositoryImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessagingService;

public class FoundProductFirebaseMessagingService extends FirebaseMessagingService {

    TokenRepository tokenRepository;

    public FoundProductFirebaseMessagingService() {
        this.tokenRepository = new TokenRepositoryImpl();
    }


    @Override
    public void onNewToken(String token) {
        Log.d("FoundProductMessaging", "Refreshed token: " + token);

        sendRegistrationToServer(token);
    }

    /**
     * Calls repository function to add current user's token to a database.
     *
     * @param token - current users's token
     */
    private void sendRegistrationToServer(String token) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        tokenRepository.addTokenToDatabase(firebaseAuth.getUid(), token);
    }
}
