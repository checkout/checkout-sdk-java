package com.checkout.payments;

import lombok.Data;

@Data
public final class PaymentActionSummary {

    private String id;

    private ActionType type;

    private String responseCode;

    private String responseSummary;

}
