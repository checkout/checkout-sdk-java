package com.checkout.payments;

import com.checkout.common.Currency;
import com.checkout.common.Link;
import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
    private static final String REDIRECT = "redirect";

    private String id;

    @SerializedName("requested_on")
    private Instant requestedOn;

    private ResponseSource source;

    private Long amount;

    private Currency currency;

    @SerializedName("payment_type")
    private String paymentType;

    private String reference;

    private String description;

    private boolean approved;

    private String status;

    @SerializedName("3ds")
    private ThreeDSData threeDS;

    private RiskAssessment risk;

    private CustomerResponse customer;

    @SerializedName("billing_descriptor")
    private BillingDescriptor billingDescriptor;

    private ShippingDetails shipping;

    @SerializedName("payment_ip")
    private String paymentIp;

    private PaymentRecipient recipient;

    private Map<String, Object> metadata = new HashMap<>();

    private String eci;

    private List<PaymentActionSummary> actions = new ArrayList<>();

    public boolean requiresRedirect() {
        return getLinks().containsKey(REDIRECT);
    }

    public Link getRedirectLink() {
        return getLink(REDIRECT);
    }

    /**
     * @deprecated Will be removed in a future version.
     */
    public PaymentActionSummary getAuthorizationAction() {
        return actions.stream()
                .filter(it -> ActionType.AUTHORIZATION.equalsIgnoreCase(it.getType()))
                .findFirst()
                .orElse(null);
    }
}