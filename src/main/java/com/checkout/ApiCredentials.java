package com.checkout;

import java.net.HttpURLConnection;

public interface ApiCredentials {
    void authorizeAsync(HttpURLConnection httpUrlConnection);
}