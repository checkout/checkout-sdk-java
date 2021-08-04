package com.checkout.payments;

import com.checkout.common.Link;
import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PaymentProcessed extends Resource {
    private String id;
    private String actionId;
    private long amount;
    private String currency;
    private boolean approved;
    private String status;
    private String authCode;
    private String responseCode;
    private String responseSummary;
    @SerializedName("3ds")
    private ThreeDSEnrollment threeDS;
    private RiskAssessment risk;
    private ResponseSource source;
    private CardDestinationResponse destination;
    private CustomerResponse customer;
    private Instant processedOn;
    private String reference;
    private String eci;
    private String schemeId;

    public Link getActionsLink() {
        return getLink("actions");
    }

    public boolean canCapture() {
        return hasLink("capture");
    }

    public Link getCaptureLink() {
        return getLink("capture");
    }

    public boolean canVoid() {
        return hasLink("void");
    }

    public Link getVoidLink() {
        return getLink("void");
    }
}