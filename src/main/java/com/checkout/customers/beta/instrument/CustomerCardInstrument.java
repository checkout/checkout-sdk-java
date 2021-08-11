package com.checkout.customers.beta.instrument;

import com.checkout.common.beta.CardCategory;
import com.checkout.common.beta.CardType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CustomerCardInstrument extends CustomerInstrument {

    private final String id;

    private final String fingerprint;

    @SerializedName("expiry_month")
    private final Integer expiryMonth;

    @SerializedName("expiry_year")
    private final Integer expiryYear;

    private final String scheme;

    private final String last4;

    private final String bin;

    @SerializedName("card_type")
    private final CardType cardType;

    @SerializedName("card_category")
    private final CardCategory cardCategory;

    private final String issuer;

    @SerializedName("issuer_country")
    private final String issuerCountry;

    @SerializedName("product_id")
    private final String productId;

    @SerializedName("product_type")
    private final String productType;

    private final AccountHolder accountHolder;

}
