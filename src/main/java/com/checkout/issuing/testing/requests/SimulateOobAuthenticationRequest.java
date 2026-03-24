package com.checkout.issuing.testing.requests;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

/**
 * Request to simulate an out-of-band (OOB) authentication request.
 */
@Data
@Builder
public class SimulateOobAuthenticationRequest {

    @SerializedName("card_id")
    private String cardId;

    @SerializedName("transaction_details")
    private OobTransactionDetails transactionDetails;

}
