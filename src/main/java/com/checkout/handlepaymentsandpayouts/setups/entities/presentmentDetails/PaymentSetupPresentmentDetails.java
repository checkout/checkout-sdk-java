package com.checkout.handlepaymentsandpayouts.setups.entities.presentmentDetails;

import com.checkout.common.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The amount and currency to present to the customer, when the settlement currency differs from the
 * customer-facing currency.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentSetupPresentmentDetails {

    /**
     * The presentment amount, in the minor currency unit.
     * [Optional]
     */
    private Long amount;

    /**
     * The presentment currency, as a three-letter ISO currency code.
     * [Optional]
     */
    private Currency currency;
}
