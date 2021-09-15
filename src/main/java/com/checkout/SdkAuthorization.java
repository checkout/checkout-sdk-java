package com.checkout;

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
            case DEFAULT:
                return credential;
            case FOUR:
            case FOUR_OAUTH:
                return BEARER + credential;
            default:
                throw new CheckoutAuthorizationException("Invalid platform type");
        }
    }

}
