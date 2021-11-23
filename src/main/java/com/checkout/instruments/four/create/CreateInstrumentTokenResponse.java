package com.checkout.instruments.four.create;

import com.checkout.common.CountryCode;
import com.checkout.common.CustomerResponse;
import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.four.InstrumentType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreateInstrumentTokenResponse extends CreateInstrumentResponse {

    private final InstrumentType type = InstrumentType.CARD;

    private String fingerprint;

    @SerializedName("expiry_month")
    private Integer expiryMonth;

    @SerializedName("expiry_year")
    private Integer expiryYear;

    private String scheme;

    private String last4;

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

    @SerializedName("customer")
    private CustomerResponse customer;

}
