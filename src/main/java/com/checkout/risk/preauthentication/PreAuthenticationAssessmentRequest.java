package com.checkout.risk.preauthentication;

import com.checkout.common.CustomerRequest;
import com.checkout.common.beta.Currency;
import com.checkout.risk.Device;
import com.checkout.risk.RiskPayment;
import com.checkout.risk.RiskShippingDetails;
import com.checkout.risk.source.RiskPaymentRequestSource;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

@Data
@Builder
public final class PreAuthenticationAssessmentRequest {

    private Instant date;

    private RiskPaymentRequestSource source;

    private CustomerRequest customer;

    private RiskPayment payment;

    private RiskShippingDetails shipping;

    private String reference;

    private String description;

    private Long amount;

    private Currency currency;

    private Device device;

    private Map<String, String> metadata;

}
