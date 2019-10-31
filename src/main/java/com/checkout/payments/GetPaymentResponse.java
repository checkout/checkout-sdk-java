package com.checkout.payments;

import com.checkout.common.Link;
import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GetPaymentResponse extends Resource {
    private String id;
    private Instant requestedOn;
    private ResponseSource source;
    private Long amount;
    private String currency;
    private String paymentType;
    private String reference;
    private String description;
    private boolean approved;
    private String status;
    @SerializedName("3ds")
    private ThreeDSEnrollment threeDS;
    private RiskAssessment risk;
    private CustomerResponse customer;
    private BillingDescriptor billingDescriptor;
    private ShippingDetails shipping;
    private String paymentIp;
    private PaymentRecipient recipient;
    private Map<String, Object> metadata = new HashMap<>();
    private String eci;
    private List<PaymentActionSummary> actions = new ArrayList<>();

    public boolean requiresRedirect() {
        return hasLink("redirect");
    }

    public Link getRedirectLink() {
        return getLink("redirect");
    }

    public PaymentActionSummary getAuthorizationAction() {
        return actions.stream()
                .filter(it -> ActionType.AUTHORIZATION.equalsIgnoreCase(it.getType()))
                .findFirst()
                .orElse(null);
    }
}