package com.checkout.common;

public class CheckoutUtils {

    public static final String TYPE = "type";
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isNullOrWhitespace(String string) {
        return string == null || string.trim().isEmpty();
    }

    public static String getVersionFromManifest() {
        return CheckoutUtils.class.getPackage().getImplementationVersion();
    }

    public static boolean isSuccessHttpStatusCode(int httpStatusCode) {
        return httpStatusCode >= 200 && httpStatusCode <= 299;
    }
}