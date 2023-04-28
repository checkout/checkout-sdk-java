package com.checkout.issuing.cards.responses;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class CardResponse extends Resource {

    protected String id;

    @SerializedName("display_name")
    protected String displayName;

    @SerializedName("last_four")
    protected String lastFour;

    @SerializedName("expiry_month")
    protected Integer expiryMonth;

    @SerializedName("expiry_year")
    protected Integer expiryYear;

    @SerializedName("billing_currency")
    protected Currency billingCurrency;

    @SerializedName("issuing_country")
    protected CountryCode issuingCountry;

    protected String reference;

    @SerializedName("created_date")
    protected Instant createdDate;
}
