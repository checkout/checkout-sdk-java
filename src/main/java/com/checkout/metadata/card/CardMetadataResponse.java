package com.checkout.metadata.card;

import com.checkout.HttpMetadata;
import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.SchemeLocalType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * Response from POST /metadata/card.
 * Contains card metadata including BIN information, scheme details, card type, issuer data,
 * and optionally card payout eligibility and scheme metadata.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class CardMetadataResponse extends HttpMetadata {

    /**
     * The issuer's Bank Identification Number (BIN). Supports 6, 8, or 11 digit BINs.
     * [Required]
     * &gt;= 6 characters
     * &lt;= 11 characters
     * Pattern: ^[0-9]{6}$|^[0-9]{8}$|^[0-9]{11}$
     */
    private String bin;

    /**
     * The global card scheme. For example, {@code american_express}, {@code cartes_bancaires},
     * {@code diners_club_international}, {@code discover}, {@code jcb}, {@code mastercard}, or {@code visa}.
     * [Required]
     */
    private String scheme;

    /**
     * The local card scheme, if the card is co-branded.
     * [Optional]
     * Enum: "cartes_bancaires" "mada" "omannet"
     *
     * @deprecated Replaced by {@link #localSchemes}. Will be removed in a future release.
     */
    @Deprecated
    private SchemeLocalType schemeLocal;

    /**
     * The local card scheme or schemes, if the card is co-branded.
     * [Optional]
     * Enum: "accel" "cartes_bancaires" "mada" "nyce" "omannet" "pulse" "shazam" "star" "upi" "paypak" "maestro"
     */
    private List<SchemeLocalType> localSchemes;

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
     * The card billing currency, as a three-letter ISO-4217 currency code.
     * [Optional]
     */
    private Currency currency;

    /**
     * The card issuer.
     * [Optional]
     */
    private String issuer;

    /**
     * The card issuer's country, as an ISO-2 code.
     * [Optional]
     */
    private CountryCode issuerCountry;

    /**
     * The card issuer's country name.
     * [Optional]
     */
    private String issuerCountryName;

    /**
     * Indicates whether the card is a combo credit and debit card. Applicable to Visa and Mastercard.
     * [Optional]
     */
    private Boolean isComboCard;

    /**
     * The card issuer or scheme's product identifier.
     * [Optional]
     */
    private String productId;

    /**
     * The card issuer or scheme's product type.
     * [Optional]
     */
    private String productType;

    /**
     * The card issuer or scheme's sub-product identifier.
     * [Optional]
     */
    private String subproductId;

    /**
     * Specifies whether the card is assigned to an interchange regulated BIN range.
     * [Optional]
     */
    private Boolean regulatedIndicator;

    /**
     * The type of the interchange regulated card BIN range.
     * [Optional]
     * Enum: "base_regulated" "fraud_protected_regulated"
     */
    private String regulatedType;

    /**
     * Indicates whether the prepaid bank identification number (BIN) is reloadable.
     * [Optional]
     */
    private Boolean isReloadablePrepaid;

    /**
     * The description of the prepaid BIN, specifying if it is an anonymous prepaid card.
     * [Optional]
     * Enum: "Anonymous prepaid program and not AMLD5 compliant"
     *       "Anonymous prepaid program and AMLD5 compliant"
     *       "Not prepaid or non-anonymous prepaid program/default"
     */
    private String anonymousPrepaidDescription;

    /**
     * Card payout eligibility details. Only present when the request {@code format} is {@code card_payouts}.
     * [Optional]
     */
    @SerializedName("card_payouts")
    private CardMetadataPayouts payouts;

    /**
     * Additional information about scheme or local scheme capabilities for US-issued cards
     * that can be used in a PINless debit network. Returned when a full card number or 11-digit BIN is provided.
     * [Optional]
     */
    private SchemeMetadata schemeMetadata;

    /**
     * Account Funding Transaction (AFT) eligibility information.
     * [Optional]
     */
    private AccountFundingTransaction accountFundingTransaction;
}
