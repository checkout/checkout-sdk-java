package com.checkout.payments;

import com.checkout.common.Resource;
import lombok.*;

import java.time.Instant;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PaymentAction extends Resource {
    private String id;
    private String type;
    private Instant processedOn;
    private int amount;
    private String authCode;
    private String responseCode;
    private String responseSummary;
    private String reference;
    private Map<String, Object> metadata;
    private boolean approved;
}