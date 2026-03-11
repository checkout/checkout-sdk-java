package com.checkout.issuing.cards.responses.renew;

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
public class RenewCardResponse extends Resource {

    @SerializedName("parent_card_id")
    private String parentCardId;

    @SerializedName("cardholder_id")
    private String cardholderId;

    private CardStatus status;

    private CardType type;

    private String id;

    @SerializedName("client_id")
    private String clientId;

    @SerializedName("entity_id")
    private String entityId;

    @SerializedName("last_four")
    private String lastFour;

    @SerializedName("expiry_year")
    private Integer expiryYear;

    @SerializedName("expiry_month")
    private Integer expiryMonth;

    @SerializedName("display_name")
    private String displayName;

    private String reference;

    @SerializedName("created_date")
    private Instant createdDate;

    @SerializedName("billing_currency")
    private Currency billingCurrency;

    @SerializedName("issuing_country")
    private CountryCode issuingCountry;
}