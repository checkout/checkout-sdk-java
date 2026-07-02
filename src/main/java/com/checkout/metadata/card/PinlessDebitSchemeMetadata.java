package com.checkout.metadata.card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PINless debit network metadata for a specific scheme. Describes the card's eligibility
 * and capabilities within that network.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class PinlessDebitSchemeMetadata {

    /**
     * The PINless debit network identifier.
     * [Optional]
     */
    private String networkId;

    /**
     * The PINless debit network name.
     * [Optional]
     */
    private String networkDescription;

    /**
     * Describes whether the card is eligible for bill payment transactions.
     * [Optional]
     */
    private Boolean billPayIndicator;

    /**
     * Describes whether the card is eligible for e-commerce transactions.
     * [Optional]
     */
    private Boolean ecommerceIndicator;

    /**
     * The type of interchange fee used for transactions.
     * [Optional]
     */
    private String interchangeFeeIndicator;

    /**
     * Describes whether the card is eligible for money transfer transactions.
     * [Optional]
     */
    private Boolean moneyTransferIndicator;

    /**
     * True indicates that the card PAN is a DPAN; false indicates it is a FPAN.
     * [Optional]
     */
    private Boolean tokenIndicator;

}
