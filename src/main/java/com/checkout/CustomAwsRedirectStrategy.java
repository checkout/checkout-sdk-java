package com.checkout;

import org.apache.hc.client5.http.impl.DefaultRedirectStrategy;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.protocol.HttpContext;

import java.net.URI;

import static com.checkout.common.CheckoutUtils.ACCEPT_JSON;
import static org.apache.hc.core5.http.HttpHeaders.ACCEPT;

/**
 * LaxRedirectStrategy sends the original headers during the first request,
 * then sends only the minimum set of headers necessary for subsequent requests
 * during the redirect. This behavior is intended to prevent the loss of authentication
 * or other critical headers during a redirect.
 * The purpose of this implementation is to exclude the original CKO Headers from the request that is sent.
 */
public class CustomAwsRedirectStrategy extends DefaultRedirectStrategy {

  public CustomAwsRedirectStrategy() {
    super();
  }

  @Override
  public URI getLocationURI(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException {
    URI redirect = super.getLocationURI(request, response, context);
    response.setHeader(ACCEPT, ACCEPT_JSON);
    return redirect;
  }
}