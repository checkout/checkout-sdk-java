package com.checkout.reconciliation;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentReportData extends Resource {

    private String id;

    private String processingCurrency;

    @SerializedName("payout_currency")
    private String payoutCurrency;

    @SerializedName("requested_on")
    private Instant requestedOn;

    @SerializedName("channel_name")
    private String channelName;

    private String reference;

    @SerializedName("payment_method")
    private String paymentMethod;

    @SerializedName("card_type")
    private String cardType;

    @SerializedName("card_category")
    private String cardCategory;

    @SerializedName("issuer_country")
    private String issuerCountry;

    @SerializedName("merchant_country")
    private String merchantCountry;

    private String mid;

    private List<Action> actions;

}
