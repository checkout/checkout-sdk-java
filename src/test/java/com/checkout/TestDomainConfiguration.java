package com.checkout;

/**
 * Every client the suite builds has to choose a domain now that the merchant-specific subdomain
 * is mandatory, so they all come through here. There are deliberately two modes.
 *
 * <p>Default: the shared hosts. The sandbox OAuth clients are not provisioned for the
 * merchant-specific subdomain, so pointing the token request at
 * {@code {subdomain}.access.sandbox.checkout.com} returns {@code invalid_client} for every
 * integration test.
 *
 * <p>Opt-in: set {@code CHECKOUT_TEST_USE_SUBDOMAIN=true} and the suite runs against
 * {@code CHECKOUT_MERCHANT_SUBDOMAIN} instead, exercising end to end the path merchants are being
 * moved to. Once sandbox is provisioned like production, set that variable in the workflows and
 * this becomes the mode CI runs in. The switch is deliberately separate from
 * {@code CHECKOUT_MERCHANT_SUBDOMAIN}, which CI already exports, so provisioning drives the change
 * rather than the presence of a secret.
 */
public final class TestDomainConfiguration {

    private TestDomainConfiguration() {
    }

    public static boolean useSubdomain() {
        return "true".equalsIgnoreCase(System.getenv("CHECKOUT_TEST_USE_SUBDOMAIN"));
    }

    @SuppressWarnings("deprecation")
    public static <T extends CheckoutApiClient> AbstractCheckoutSdkBuilder<T> configureDomain(
            final AbstractCheckoutSdkBuilder<T> builder) {
        final String subdomain = System.getenv("CHECKOUT_MERCHANT_SUBDOMAIN");
        if (useSubdomain() && subdomain != null && !subdomain.trim().isEmpty()) {
            return builder.environmentSubdomain(subdomain);
        }
        return builder.useLegacyDomain();
    }
}
