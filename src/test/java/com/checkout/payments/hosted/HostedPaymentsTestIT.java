package com.checkout.payments.hosted;

import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class HostedPaymentsTestIT extends SandboxTestFixture {

    public static final String REFERENCE = "ORD-123A";

    @Test
    public void shouldCreateHostedPayments() throws ExecutionException, InterruptedException {
        final HostedPaymentRequest request = TestHelper.createHostedPaymentRequest(REFERENCE);
        final HostedPaymentResponse response = getApi().hostedPaymentsClient().createAsync(request).get();
        assertNotNull(response);
        assertEquals(REFERENCE, response.getReference());
        assertNotNull(response.getLinks());
        assertTrue(response.getLinks().containsKey("redirect"));
        HostedPaymentRequest.builder().build();
    }
}