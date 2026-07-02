package com.checkout.tokens;

import com.checkout.HttpMetadata;
import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.CountryCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
public class TokenResponse extends HttpMetadata {

    private TokenType type;

    private String token;

    private Instant expiresOn;

    private Integer expiryMonth;

    private Integer expiryYear;

    private String scheme;

    private String last4;

    private String bin;

    private CardType cardType;

    private CardCategory cardCategory;

    private String issuer;

    private CountryCode issuerCountry;

    private String productId;

    private String productType;

    private String tokenFormat;

    //Only available in CS2

    private String schemeLocal;

}
