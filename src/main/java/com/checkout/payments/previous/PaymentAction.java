package com.checkout.payments.previous;

import com.checkout.common.Resource;
import com.checkout.payments.ActionType;
import com.checkout.payments.Processing;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentAction extends Resource {

    private String id;

    private ActionType type;

    private Instant processedOn;

    private Long amount;

    private Boolean approved;

    private String authCode;

    private String responseCode;

    private String responseSummary;

    private Processing processing;

    private String reference;

    private Map<String, Object> metadata = new HashMap<>();

}
