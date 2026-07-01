package com.checkout.payments.previous.response.source;

import com.checkout.common.Address;
import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.CountryCode;
import com.checkout.common.Phone;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CardResponseSource extends AbstractResponseSource implements ResponseSource {

    private Address billingAddress;

    private Phone phone;

    private Integer expiryMonth;

    private Integer expiryYear;

    private String name;

    private String scheme;

    private String last4;

    private String fingerprint;

    private String bin;

    private CardType cardType;

    private CardCategory cardCategory;

    private String issuer;

    private CountryCode issuerCountry;

    private String productId;

    private String productType;

    private String avsCheck;

    private String cvvCheck;

    private Boolean payouts;

    private String fastFunds;

    private String paymentAccountReference;

}