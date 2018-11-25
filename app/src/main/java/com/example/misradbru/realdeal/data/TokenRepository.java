package com.example.misradbru.realdeal.data;

public interface TokenRepository {

    void addToken(String uid, String token);

    void updateUid(String uid, String token);
}
