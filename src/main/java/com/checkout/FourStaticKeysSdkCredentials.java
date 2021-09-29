package com.checkout;

final class FourStaticKeysSdkCredentials extends AbstractStaticKeysSdkCredentials {

    private static final String FOUR_SECRET_KEY_PATTERN = "^sk_(sbox_)?[a-z2-7]{26}[a-z2-7*#$=]$";
    private static final String FOUR_PUBLIC_KEY_PATTEN = "^pk_(sbox_)?[a-z2-7]{26}[a-z2-7*#$=]$";

    FourStaticKeysSdkCredentials(final String secretKey, final String publicKey) {
        super(PlatformType.FOUR, FOUR_SECRET_KEY_PATTERN, FOUR_PUBLIC_KEY_PATTEN, secretKey, publicKey);
    }

    @Override
    public SdkAuthorization getAuthorization(final SdkAuthorizationType authorizationType) {
        switch (authorizationType) {
            case SECRET_KEY:
            case SECRET_KEY_OR_OAUTH:
                return new SdkAuthorization(platformType, secretKey);
            case PUBLIC_KEY:
            case PUBLIC_KEY_OR_OAUTH:
                return new SdkAuthorization(platformType, publicKey);
            default:
                throw CheckoutAuthorizationException.invalidAuthorization(authorizationType);
        }
    }

}
