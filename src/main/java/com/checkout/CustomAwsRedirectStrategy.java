package com.checkout;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.protocol.HttpContext;

import static com.checkout.common.CheckoutUtils.ACCEPT_JSON;
import static org.apache.http.HttpHeaders.ACCEPT;

/**
 * LaxRedirectStrategy sends the original headers during the first request,
 * then sends only the minimum set of headers necessary for subsequent requests
 * during the redirect. This behavior is intended to prevent the loss of authentication
 * or other critical headers during a redirect.
 * The purpose of this implementation is to exclude the original CKO Headers from the request that is sent.
 */
public class CustomAwsRedirectStrategy extends DefaultRedirectStrategy {

    public CustomAwsRedirectStrategy() {
        super(new String[]{
                HttpGet.METHOD_NAME
        });
    }

    @Override
    public HttpUriRequest getRedirect(final HttpRequest request,
                                      final HttpResponse response,
                                      final HttpContext context) throws ProtocolException {
        final HttpUriRequest redirect = super.getRedirect(request, response, context);
        redirect.setHeader(ACCEPT, ACCEPT_JSON);
        return redirect;
    }
}