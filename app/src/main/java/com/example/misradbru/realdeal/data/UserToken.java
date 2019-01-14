package com.example.misradbru.realdeal.data;

/**
 * Class that represents FCM token for a user
 */
public class UserToken {
    private String uid;
    private String token;

    public UserToken(String uid, String token) {
        this.uid = uid;
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
