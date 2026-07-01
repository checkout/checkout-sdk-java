package com.checkout.instruments.previous;

import com.checkout.HttpMetadata;
import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.CountryCode;
import com.checkout.common.InstrumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InstrumentDetails extends HttpMetadata {

    private String id;

    private InstrumentType type;

    private String fingerprint;

    private Integer expiryMonth;

    private Integer expiryYear;

    private String name;

    private String scheme;

    private String last4;

    private String bin;

    private CardType cardType;

    private CardCategory cardCategory;

    private String issuer;

    private CountryCode issuerCountry;

    private String productId;

    private String productType;

    private InstrumentAccountHolder accountHolder;

}
