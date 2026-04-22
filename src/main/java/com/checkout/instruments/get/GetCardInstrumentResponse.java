package com.checkout.instruments.get;

import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.CardWalletType;
import com.checkout.common.CountryCode;
import com.checkout.common.InstrumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GetCardInstrumentResponse extends GetInstrumentResponse {

    private final InstrumentType type = InstrumentType.CARD;

    /**
     * The expiry month of the card.
     * [Optional]
     */
    private Integer expiryMonth;

    /**
     * The expiry year of the card.
     * [Optional]
     */
    private Integer expiryYear;

    /**
     * The name of the cardholder.
     * [Optional]
     */
    private String name;

    /**
     * The card scheme.
     * [Optional]
     */
    private String scheme;

    /**
     * The local card scheme.
     * [Optional]
     */
    private String schemeLocal;

    /**
     * The last four digits of the card number.
     * [Optional]
     */
    private String last4;

    /**
     * The card BIN (Bank Identification Number).
     * [Optional]
     */
    private String bin;

    /**
     * The card type.
     * [Optional]
     */
    private CardType cardType;

    /**
     * The card category.
     * [Optional]
     */
    private CardCategory cardCategory;

    /**
     * The name of the card issuer.
     * [Optional]
     */
    private String issuer;

    /**
     * The country of the card issuer.
     * [Optional]
     */
    private CountryCode issuerCountry;

    /**
     * The card product identifier.
     * [Optional]
     */
    private String productId;

    /**
     * The card product type.
     * [Optional]
     */
    private String productType;

    /**
     * The JWE-encrypted full card number. This is only present if your level of PCI compliance is SAQ-D.
     * [Optional]
     */
    private String encryptedCardNumber;

    /**
     * The network token associated with this instrument.
     * [Optional]
     */
    private InstrumentNetworkToken networkToken;

    /**
     * The card wallet type used for this instrument.
     * [Optional]
     * Enum: "googlepay" "applepay"
     */
    private CardWalletType cardWalletType;

    /**
     * Indicates whether the card is subject to interchange regulation.
     * [Required]
     */
    private Boolean regulatedIndicator;

}
