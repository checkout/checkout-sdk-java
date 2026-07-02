package com.checkout.instruments.create;

import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.CountryCode;
import com.checkout.common.InstrumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreateInstrumentTokenResponse extends CreateInstrumentResponse {

    private final InstrumentType type = InstrumentType.CARD;

    private Integer expiryMonth;

    private Integer expiryYear;

    private String scheme;

    private String schemeLocal;

    private String last4;

    private String bin;

    private CardType cardType;

    private CardCategory cardCategory;

    private String issuer;

    private CountryCode issuerCountry;

    private String productId;

    private String productType;

}
