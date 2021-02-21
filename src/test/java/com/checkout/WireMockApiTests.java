package com.checkout;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class WireMockApiTests extends WiremockTestFixture {
    @Test(expected = CheckoutApiException.class)
    public void test_calling_api_with_403() throws Throwable {
        final String paymentId = "test_payment";

        stubFor(get(urlEqualTo("/payments/" + paymentId))
        .withHeader("Authorization", equalTo("test_secret_key"))
        .willReturn(aResponse()
        .withStatus(403)));

        try {
            api.paymentsClient().getAsync(paymentId).get();
        } catch(ExecutionException e) {
            throw e.getCause();
        }
    }
}
