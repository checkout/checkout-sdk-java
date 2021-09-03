package com.checkout.payments.hosted;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HostedPaymentsTestIT extends SandboxTestFixture {

    static final String REFERENCE = "ORD-123A";

    HostedPaymentsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldCreateHostedPayments() throws ExecutionException, InterruptedException {
        final HostedPaymentRequest request = TestHelper.createHostedPaymentRequest(REFERENCE);
        final HostedPaymentResponse response = getApi().hostedPaymentsClient().createAsync(request).get();
        assertNotNull(response);
        assertEquals(REFERENCE, response.getReference());
        assertNotNull(response.getLinks());
        assertTrue(response.getLinks().containsKey("redirect"));
        HostedPaymentRequest.builder().build();
    }
}