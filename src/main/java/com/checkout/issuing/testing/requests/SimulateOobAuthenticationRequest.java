package com.checkout.issuing.testing.requests;

import lombok.Builder;
import lombok.Data;

/**
 * Request to simulate an out-of-band (OOB) authentication request.
 */
@Data
@Builder
public class SimulateOobAuthenticationRequest {

    private String cardId;

    private OobTransactionDetails transactionDetails;

}
