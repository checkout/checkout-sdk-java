package com.checkout.disputes;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentDispute {

    private String id;
    private String actionId;
    private Long amount;
    private String currency;
    private String method;
    private String arn;
    private String processedOn;

}
