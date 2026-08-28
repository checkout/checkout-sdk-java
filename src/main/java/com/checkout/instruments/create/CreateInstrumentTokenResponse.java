package com.checkout.instruments.create;

import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.CountryCode;
import com.checkout.common.InstrumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Card instrument response.
 *
 * <p>The id, the fingerprint and the customer are inherited from
 * {@link CreateInstrumentResponse}. The instrument type is card rather than token: the
 * specification maps the token store request onto a card store response.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreateInstrumentTokenResponse extends CreateInstrumentResponse {

    /**
     * The type of instrument.
     * [Required]
     */
    private final InstrumentType type = InstrumentType.CARD;

    /**
     * The expiry month.
     * [Required]
     * max 2 characters
     * min 1
     */
    private Integer expiryMonth;

    /**
     * The expiry year.
     * [Required]
     * min 4 characters
     * max 4 characters
     */
    private Integer expiryYear;

    /**
     * The card scheme.
     * [Optional]
     */
    private String scheme;

    /**
     * The local co-branded card scheme.
     * [Optional]
     * Enum: "cartes_bancaires"
     */
    private String schemeLocal;

    /**
     * The last four digits of the card number.
     * [Required]
     * min 4 characters
     * max 4 characters
     */
    private String last4;

    /**
     * The card issuer's bank identification number (BIN).
     * [Required]
     */
    private String bin;

    /**
     * The card type.
     * [Optional]
     * Enum: "CREDIT" "DEBIT" "PREPAID" "CHARGE"
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
     * The card issuer's country, as a two-letter ISO code.
     * [Optional]
     * min 2 characters
     * max 2 characters
     */
    private CountryCode issuerCountry;

    /**
     * The issuer or card scheme product identifier.
     * [Optional]
     */
    private String productId;

    /**
     * The issuer or card scheme product type.
     * [Optional]
     */
    private String productType;

}
