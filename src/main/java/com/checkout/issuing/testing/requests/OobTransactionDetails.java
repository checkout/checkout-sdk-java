package com.checkout.issuing.testing.requests;

import lombok.Builder;
import lombok.Data;

/**
 * Details for the simulated OOB authentication transaction.
 */
@Data
@Builder
public class OobTransactionDetails {

    private String lastFour;

    private String merchantName;

    private Double purchaseAmount;

    private String purchaseCurrency;

}
