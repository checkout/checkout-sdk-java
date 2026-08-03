package com.checkout.sessions;

import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.CountryCode;
import lombok.Data;

/**
 * Additional details for this card.
 * <p>
 * Returned as {@link CardInfo#getMetadata()} on the session responses.
 */
@Data
public final class SessionsCardMetadataResponse {

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
     * The card issuer's name.
     * [Optional]
     */
    private String issuerName;

    /**
     * The two letter alpha country code of the card issuer.
     * [Optional]
     * ^[A-Z]{2}
     */
    private CountryCode issuerCountry;

    /**
     * The issuer/card scheme product identifier.
     * [Optional]
     */
    private String productId;

    /**
     * The issuer/card scheme product type.
     * [Optional]
     */
    private String productType;

}
