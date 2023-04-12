package com.checkout.transfers;

import com.checkout.CheckoutApiException;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class TransfersTestIT extends SandboxTestFixture {

    TransfersTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Test
    void shouldInitiateTransferOfFunds_idempotently() {

        final CreateTransferRequest transferRequest = CreateTransferRequest.builder()
                .transferType(TransferType.COMMISSION)
                .source(TransferSourceRequest.builder()
                        .id("ent_kidtcgc3ge5unf4a5i6enhnr5m")
                        .amount(100L)
                        .build())
                .destination(TransferDestinationRequest.builder()
                        .id("ent_w4jelhppmfiufdnatam37wrfc4")
                        .build())
                .build();

        final String idempotencyKey = UUID.randomUUID().toString();

        final CreateTransferResponse response = blocking(() -> checkoutApi.transfersClient().initiateTransferOfFunds(transferRequest, idempotencyKey));
        assertNotNull(response.getId());
        assertEquals(TransferStatus.PENDING, response.getStatus());

        try {
            checkoutApi.transfersClient().initiateTransferOfFunds(transferRequest, idempotencyKey).get();
            fail("Should not get here!");
        } catch (final InterruptedException | ExecutionException e) {
            assertTrue(e.getCause() instanceof CheckoutApiException);
            final CheckoutApiException checkoutException = (CheckoutApiException) e.getCause();
            assertEquals(409, checkoutException.getHttpStatusCode());
        }
    }

}