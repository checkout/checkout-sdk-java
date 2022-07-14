package com.checkout;

import java.util.Objects;

import static com.checkout.common.CheckoutUtils.validateParams;

public final class SdkAuthorization {

    private static final String BEARER = "Bearer ";
    private final PlatformType platformType;
    private final String credential;

    public SdkAuthorization(final PlatformType platformType, final String credential) {
        validateParams("platformType", platformType);
        this.platformType = platformType;
        this.credential = credential;
    }

    public String getAuthorizationHeader() {
        switch (platformType) {
            case PREVIOUS:
            case CUSTOM:
                return credential;
            case DEFAULT:
            case DEFAULT_OAUTH:
                return BEARER + credential;
            default:
                throw new CheckoutAuthorizationException("Invalid platform type");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SdkAuthorization that = (SdkAuthorization) o;
        return platformType == that.platformType &&
                credential.equals(that.credential);
    }

    @Override
    public int hashCode() {
        return Objects.hash(platformType, credential);
    }

}
