package com.checkout;

public abstract class SdkCredentials {

    protected final PlatformType platformType;

    protected SdkCredentials(final PlatformType platformType) {
        this.platformType = platformType;
    }

    public PlatformType getPlatformType() {
        return platformType;
    }

    public abstract SdkAuthorization getAuthorization(final SdkAuthorizationType authorizationType);

}
