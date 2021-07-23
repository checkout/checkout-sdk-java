package com.checkout.beta;

import com.checkout.ApiClient;
import com.checkout.ApiClientImpl;
import com.checkout.CheckoutArgumentException;
import com.checkout.CheckoutConfiguration;
import com.checkout.Environment;
import com.google.common.annotations.Beta;

import java.net.URI;

@Beta
public final class Checkout {

    private Checkout() {
    }

    public static CheckoutApiBuilder staticKeys() {
        return new CheckoutApiBuilder();
    }

    public static class CheckoutApiBuilder {

        private Environment environment;
        private String publicKey;
        private String secretKey;
        private URI uri;

        public CheckoutApiBuilder publicKey(final String publicKey) {
            this.publicKey = publicKey;
            return this;
        }

        public CheckoutApiBuilder secretKey(final String secretKey) {
            this.secretKey = secretKey;
            return this;
        }

        public CheckoutApiBuilder environment(final Environment environment) {
            this.environment = environment;
            return this;
        }

        public CheckoutApiBuilder environment(final String environment) {
            this.environment = Environment.lookup(environment);
            return this;
        }

        public CheckoutApiBuilder uri(final URI uri) {
            this.uri = uri;
            return this;
        }

        public CheckoutApi build() {
            final CheckoutConfiguration checkoutConfiguration = getConfiguration();
            final ApiClient client = new ApiClientImpl(checkoutConfiguration);
            return new CheckoutApiImpl(client, checkoutConfiguration);
        }

        private CheckoutConfiguration getConfiguration() {
            if (environment == null && uri == null) {
                throw new CheckoutArgumentException("environment or URI must be specified");
            }
            if (uri == null) {
                return new CheckoutConfiguration(publicKey, secretKey, environment);
            }
            return new CheckoutConfiguration(publicKey, secretKey, uri);
        }

    }

}
