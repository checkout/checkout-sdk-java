package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paybybank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A bank available for the customer to select for a Pay by Bank payment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PayByBankBank {

    /**
     * The unique identifier of the bank.
     * [Optional]
     */
    private String bankId;

    /**
     * The display name of the bank.
     * [Optional]
     */
    private String displayName;

    /**
     * The URL of the bank's logo.
     * [Optional]
     */
    private String logoUrl;

    /**
     * Whether the bank is currently available for selection.
     * [Optional]
     */
    private Boolean available;
}
