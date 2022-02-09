package com.checkout;

import com.checkout.common.CheckoutUtils;

abstract class AbstractStaticKeysSdkCredentials extends SdkCredentials {

    protected final String secretKey;
    protected final String publicKey;

    protected AbstractStaticKeysSdkCredentials(final PlatformType platformType,
                                               final String secretKeyPattern,
                                               final String publicKeyPattern,
                                               final String secretKey,
                                               final String publicKey) {
        super(platformType);
        validateSecretKey(secretKey, secretKeyPattern);
        validatePublicKey(publicKey, publicKeyPattern);
        this.secretKey = secretKey;
        this.publicKey = publicKey;
    }

    protected void validateSecretKey(final String secretKey, final String secretKeyPattern) {
        CheckoutUtils.validateParams("secretKey", secretKey);
        if (validKey(secretKey, secretKeyPattern)) {
            return;
        }
        throw new CheckoutArgumentException("invalid secret key");
    }

    protected void validatePublicKey(final String publicKey, final String publicKeyPattern) {
        // public key is not strictly mandatory
        if (publicKey == null) {
            return;
        }
        if (validKey(publicKey, publicKeyPattern)) {
            return;
        }
        throw new CheckoutArgumentException("invalid public key");
    }

    private boolean validKey(final String key, final String pattern) {
        return key.matches(pattern);
    }

    String getSecretKey() {
        return secretKey;
    }

    String getPublicKey() {
        return publicKey;
    }

}
