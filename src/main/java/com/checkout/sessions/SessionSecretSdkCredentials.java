package com.checkout.sessions;

import com.checkout.CheckoutAuthorizationException;
import com.checkout.PlatformType;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;

import static com.checkout.common.CheckoutUtils.validateParams;

final class SessionSecretSdkCredentials extends SdkCredentials {

    private final String secret;

    SessionSecretSdkCredentials(final String secret) {
        super(PlatformType.CUSTOM);
        validateParams("secret", secret);
        this.secret = secret;
    }

    @Override
    public SdkAuthorization getAuthorization(final SdkAuthorizationType authorizationType) {
        if (SdkAuthorizationType.CUSTOM.equals(authorizationType)) {
            return new SdkAuthorization(platformType, secret);
        }
        throw CheckoutAuthorizationException.invalidAuthorization(authorizationType);
    }

    String getSecret() {
        return secret;
    }
}
