package com.checkout.metadata.card;

import com.google.gson.annotations.SerializedName;
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
    @SerializedName("network_id")
    private String networkId;

    /**
     * The PINless debit network name.
     * [Optional]
     */
    @SerializedName("network_description")
    private String networkDescription;

    /**
     * Describes whether the card is eligible for bill payment transactions.
     * [Optional]
     */
    @SerializedName("bill_pay_indicator")
    private Boolean billPayIndicator;

    /**
     * Describes whether the card is eligible for e-commerce transactions.
     * [Optional]
     */
    @SerializedName("ecommerce_indicator")
    private Boolean ecommerceIndicator;

    /**
     * The type of interchange fee used for transactions.
     * [Optional]
     */
    @SerializedName("interchange_fee_indicator")
    private String interchangeFeeIndicator;

    /**
     * Describes whether the card is eligible for money transfer transactions.
     * [Optional]
     */
    @SerializedName("money_transfer_indicator")
    private Boolean moneyTransferIndicator;

    /**
     * True indicates that the card PAN is a DPAN; false indicates it is a FPAN.
     * [Optional]
     */
    @SerializedName("token_indicator")
    private Boolean tokenIndicator;

}
