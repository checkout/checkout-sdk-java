package com.checkout.tokens;

import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.Resource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Token metadata response returned by GET /tokens/{tokenId}/metadata.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class TokenMetadataResponse extends Resource {

    /**
     * The token ID.
     * [Required]
     */
    private String token;

    /**
     * The token type.
     * [Required]
     */
    private String type;

    /**
     * The date/time the token will expire.
     * [Required]
     * Format: date-time (RFC 3339)
     */
    private Instant expiresOn;

    /**
     * The card expiry month.
     * [Required]
     * min 1, max 12
     */
    private Integer expiryMonth;

    /**
     * The card expiry year.
     * [Required]
     */
    private Integer expiryYear;

    /**
     * The card scheme.
     * [Optional]
     */
    private String scheme;

    /**
     * The last four digits of the card number.
     * [Required]
     */
    private String last4;

    /**
     * The card issuer BIN.
     * [Required]
     */
    private String bin;

    /**
     * The card type.
     * [Optional]
     * Enum: "CREDIT" "DEBIT" "PREPAID" "CHARGE" "DEFERRED DEBIT"
     */
    private CardType cardType;

    /**
     * The card category.
     * [Optional]
     * Enum: "CONSUMER" "COMMERCIAL"
     */
    private CardCategory cardCategory;

    /**
     * The name of the card issuer.
     * [Optional]
     */
    private String issuer;

    /**
     * The card issuer's country (two-letter ISO code).
     * [Optional]
        * min 2 characters
        * max 2 characters
     */
    private String issuerCountry;

    /**
     * The issuer or card scheme's product identifier.
     * [Optional]
     */
    private String productId;

    /**
     * The issuer or card scheme's product type.
     * [Optional]
     */
    private String productType;

    /**
     * Partial billing address — city and country only.
     * [Optional]
     */
    private TokenMetadataBillingAddress billingAddress;
}
