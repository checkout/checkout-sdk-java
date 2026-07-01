package com.checkout.reconciliation.previous;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentReportData extends Resource {

    private String id;

    private Currency processingCurrency;

    private Currency payoutCurrency;

    private String requestedOn;

    private String channelName;

    private String reference;

    private String paymentMethod;

    private String cardType;

    private String cardCategory;

    private CountryCode issuerCountry;

    private CountryCode merchantCountry;

    private String region;

    private String mid;

    private List<Action> actions;

}

