package com.checkout.sessions;

import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.CountryCode;

public final class SessionsCardMetadataResponse {

    private CardType cardType;

    private CardCategory cardCategory;

    private String issuerName;

    private CountryCode issuerCountry;

    private String productId;

    private String productType;

}
