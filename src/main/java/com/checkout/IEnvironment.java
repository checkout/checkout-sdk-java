package com.checkout;

import java.net.URI;

public interface IEnvironment {

    URI getCheckoutApi();

    URI getFilesApi();

    URI getTransfersApi();

    URI getBalancesApi();

    URI getOAuthAuthorizationApi();

    boolean isSandbox();
}
