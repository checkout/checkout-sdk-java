package com.checkout.payments.previous.response.destination;

import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.CountryCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentResponseCardDestination extends AbstractPaymentResponseDestination implements PaymentResponseDestination {

    private Integer expiryMonth;

    private Integer expiryYear;

    private String name;

    private String last4;

    private String fingerprint;

    private String bin;

    private CardType cardType;

    private CardCategory cardCategory;

    private String issuer;

    private CountryCode issuerCountry;

    private String productId;

    private String productType;

}
