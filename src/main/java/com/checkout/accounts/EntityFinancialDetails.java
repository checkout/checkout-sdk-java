package com.checkout.accounts;

import com.checkout.common.Currency;
import lombok.Builder;
import lombok.Data;

/**
 * Seller financial questions ({@code financial_details}): on the company of EEA and US Company Full
 * and Lite (2.0), and on the individual of US Sole Trader Full and Lite (2.0).
 */
@Data
@Builder
public final class EntityFinancialDetails {

    /**
     * The estimated annual processing volume. In minor units without decimals.
     * [Required] on the Full (2.0) variants; [Optional] on the Lite (2.0) variants.
     * min 0
     */
    private Long annualProcessingVolume;

    /**
     * The expected average transaction value. In minor units without decimals.
     * [Required] on the Full (2.0) variants; [Optional] on the Lite (2.0) variants.
     * min 0
     */
    private Long averageTransactionValue;

    /**
     * The expected highest transaction value. In minor units without decimals.
     * [Required] on the Full (2.0) variants; [Optional] on the Lite (2.0) variants.
     * min 0
     */
    private Long highestTransactionValue;

    /**
     * Not defined by any Accounts API schema; the API does not read it. Supporting documents go on
     * the top-level request documents ({@link OnboardSubEntityDocuments}) instead.
     *
     * @deprecated Not part of any Accounts API schema. Will be removed in a future major version.
     */
    @Deprecated
    private EntityFinancialDocuments documents;

    /**
     * The currency used for the financial details provided.
     * [Required] on US Company Full and US Sole Trader Full (2.0); [Optional] on the other variants.
     */
    private Currency currency;

}
