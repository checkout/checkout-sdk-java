package com.checkout.accounts;

import com.checkout.common.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The sub-entity's expected processing, as returned by GET /accounts/entities/{id}
 * ({@code processing_details}, Accounts API v3.0).
 *
 * <p>A response-only type, separate from the request's {@link ProcessingDetails}: the amounts are
 * {@code Long} here because the API declares them as integers in minor units with no maximum, and
 * an {@code Integer} would fail to read any value above 2,147,483,647.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class EntityProcessingDetails {

    /**
     * The country code (iso-3166-1 alpha-2) where the settlement bank account is located.
     * Format: iso-3166-1-alpha-2
     * 2 characters
     */
    private String settlementCountry;

    /**
     * Target country codes (iso-3166-1 alpha-2) with more than 10% expected volume processing with
     * Checkout.com.
     * min 1 item, max 10 items
     */
    private List<String> targetCountries;

    /**
     * The estimated annual processing volume. In minor units without decimals.
     * min 0
     */
    private Long annualProcessingVolume;

    /**
     * The expected average transaction value. In minor units without decimals.
     * min 0
     */
    private Long averageTransactionValue;

    /**
     * The expected highest transaction value. In minor units without decimals.
     * min 0
     */
    private Long highestTransactionValue;

    /**
     * The currency used for the processing details provided.
     */
    private Currency currency;

}
