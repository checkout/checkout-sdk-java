package com.checkout.handlepaymentsandpayouts.setups.entities.accountFundingTransaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Sender identification details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AccountFundingTransactionIdentification {

    /**
     * The type of identification used to identify the sender.
     * [Optional]
     * Enum: "passport" "driving_license" "national_id"
     */
    private AccountFundingTransactionIdentificationType type;

    /**
     * The identification number.
     * [Optional]
     */
    private String number;

    /**
     * The two-letter ISO country code of the country that issued the identification.
     * [Optional]
     */
    private String issuingCountry;
}
