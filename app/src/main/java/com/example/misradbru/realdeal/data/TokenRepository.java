package com.example.misradbru.realdeal.data;

public interface TokenRepository {

    void addTokenToDatabase(String uid, String token);

    void updateUid(String uid, String token);

    void updateToken(String uid, String token);

    void addNewUserAndToken(String uid, String token);

    void deleteTokenForDevice(String uid, String token);
}
