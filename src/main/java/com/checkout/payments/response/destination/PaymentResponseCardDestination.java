package com.checkout.payments.response.destination;

import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentResponseCardDestination extends AbstractPaymentResponseDestination implements PaymentResponseDestination {

    @SerializedName("expiry_month")
    private Integer expiryMonth;

    @SerializedName("expiry_year")
    private Integer expiryYear;

    private String name;

    private String last4;

    private String fingerprint;

    private String bin;

    @SerializedName("card_type")
    private CardType cardType;

    @SerializedName("card_category")
    private CardCategory cardCategory;

    private String issuer;

    @SerializedName("issuer_country")
    private CountryCode issuerCountry;

    @SerializedName("product_id")
    private String productId;

    @SerializedName("product_type")
    private String productType;

}
