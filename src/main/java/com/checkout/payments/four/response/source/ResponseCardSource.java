package com.checkout.payments.four.response.source;

import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Phone;
import com.checkout.common.four.CardCategory;
import com.checkout.common.four.CardType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ResponseCardSource extends ResponseSource {

    private final String id;

    @SerializedName("billing_address")
    private final Address billingAddress;

    private final Phone phone;

    private final String number;

    @SerializedName("expiry_month")
    private final Integer expiryMonth;

    @SerializedName("expiry_year")
    private final Integer expiryYear;

    private final String name;

    private final String scheme;

    private final String last4;

    private final String fingerprint;

    private final String bin;

    @SerializedName("card_type")
    private final CardType cardType;

    @SerializedName("card_category")
    private final CardCategory cardCategory;

    private final String issuer;

    @SerializedName("issuer_country")
    private final CountryCode issuerCountry;

    @SerializedName("product_id")
    private final String productId;

    @SerializedName("product_type")
    private final String productType;

    @SerializedName("avs_check")
    private final String avsCheck;

    @SerializedName("cvv_check")
    private final String cvvCheck;

    private final boolean payouts;

    @SerializedName("fast_funds")
    private final String fastFunds;

    @SerializedName("payment_account_reference")
    private final String paymentAccountReference;

}
