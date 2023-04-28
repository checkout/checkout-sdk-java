package com.checkout.issuing.cards.responses;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Resource;
import com.checkout.issuing.cards.CardStatus;
import com.checkout.issuing.cards.CardType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractCardDetailsResponse extends Resource {

    protected final CardType type;

    protected String id;

    @SerializedName("cardholder_id")
    protected String cardholderId;

    @SerializedName("card_product_id")
    protected String cardProductId;

    @SerializedName("client_id")
    protected String clientId;

    @SerializedName("last_four")
    protected String lastFour;

    @SerializedName("expiry_month")
    protected Integer expiryMonth;

    @SerializedName("expiry_year")
    protected Integer expiryYear;

    protected CardStatus status;

    @SerializedName("display_name")
    protected String displayName;

    @SerializedName("billing_currency")
    protected Currency billingCurrency;

    @SerializedName("issuing_country")
    protected CountryCode issuingCountry;

    protected String reference;

    @SerializedName("created_date")
    protected Instant createdDate;

    @SerializedName("last_modified_date")
    protected Instant lastModifiedDate;

    protected AbstractCardDetailsResponse(final CardType type) {
        this.type = type;
    }
}
