package com.checkout.balances;

import com.checkout.HttpMetadata;
import com.checkout.common.Currency;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The bank details and payment reference used to top up a sub-account.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class TopUpInstructionsResponse extends HttpMetadata {

    /**
     * The unique identifier of the sub-account that the instructions apply to.
     * [Required]
     */
    private String currencyAccountId;

    /**
     * The currency that funds must be sent in, as a three-letter ISO 4217 currency code.
     * This is the sub-account's holding currency, returned as {@code holding_currency} by the
     * Retrieve entity balances endpoint.
     * [Required]
     */
    private Currency currency;

    /**
     * The reference that must be quoted on the payment. It is how an incoming payment is
     * attributed to the sub-account. A payment sent without this reference may not be credited.
     * [Required]
     */
    private String paymentReference;

    /**
     * The bank details for each available funding rail.
     * [Required]
     */
    private TopUpBankDetails bankDetails;

}
