package com.checkout;

public enum PlatformType {

    CLASSIC("^sk_([a-z]+)_(\\w{8})-(\\w{4})-(\\w{4})-(\\w{4})-(\\w{12})$", "^pk_([a-z]+)_(\\w{8})-(\\w{4})-(\\w{4})-(\\w{4})-(\\w{12})$"),
    FOUR("^sk_([a-z]+)_?[a-z2-7]{26}[a-z2-7*#$=]$", "^pk_([a-z]+)_?[a-z2-7]{26}[a-z2-7*#$=]$");

    private final String secretKeyPattern;
    private final String publicKeyPattern;

    PlatformType(final String secretKeyPattern, final String publicKeyPattern) {
        this.secretKeyPattern = secretKeyPattern;
        this.publicKeyPattern = publicKeyPattern;
    }

    public void validateSecretKey(final String key) {
        if (validateKey(key, this.secretKeyPattern)) {
            return;
        }
        throw new CheckoutArgumentException("invalid secret key");
    }

    public void validatePublicKey(final String key) {
        if (validateKey(key, this.publicKeyPattern)) {
            return;
        }
        throw new CheckoutArgumentException("invalid public key");
    }

    public boolean validateKey(final String key, final String pattern) {
        return key.matches(pattern);
    }

}
