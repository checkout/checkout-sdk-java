package com.checkout.reconciliation;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentReportData extends Resource {

    private String id;

    @SerializedName("processing_currency")
    private Currency processingCurrency;

    @SerializedName("payout_currency")
    private Currency payoutCurrency;

    @SerializedName("requested_on")
    private String requestedOn;

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
    private CountryCode issuerCountry;

    @SerializedName("merchant_country")
    private CountryCode merchantCountry;

    private String region;

    private String mid;

    private List<Action> actions;

}

