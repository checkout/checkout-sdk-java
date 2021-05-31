package com.checkout.reconciliation;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PaymentReportData extends Resource {

    private String id;
    private String processingCurrency;
    private String payoutCurrency;
    private String channelName;
    private String reference;
    private String paymentMethod;
    private String cardType;
    private String cardCategory;
    private String issuerCountry;
    private String merchantCountry;
    private String mid;
    private Instant requestedOn;
    private List<Action> actions = new ArrayList<>();
}
