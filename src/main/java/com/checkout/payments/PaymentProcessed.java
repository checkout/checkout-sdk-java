package com.checkout.payments;

import com.checkout.common.Currency;
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
public final class PaymentProcessed extends Resource {

    private String id;

    @SerializedName("action_id")
    private String actionId;

    private long amount;

    private Currency currency;

    private boolean approved;

    private String status;

    @SerializedName("auth_code")
    private String authCode;

    @SerializedName("response_code")
    private String responseCode;

    @SerializedName("response_summary")
    private String responseSummary;

    @SerializedName("3ds")
    private ThreeDSEnrollmentData threeDS;

    private RiskAssessment risk;

    private ResponseSource source;

    private CardDestinationResponse destination;

    private CustomerResponse customer;

    @SerializedName("processed_on")
    private Instant processedOn;

    private String reference;

    private PaymentProcessing processing;

    private String eci;

    @SerializedName("scheme_id")
    private String schemeId;

    /**
     * @deprecated Will be removed in a future version.
     */
    @Deprecated
    public Link getActionsLink() {
        return getLink("actions");
    }

    /**
     * @deprecated Will be removed in a future version.
     */
    public boolean canCapture() {
        return getLinks().containsKey("capture");
    }

    /**
     * @deprecated Will be removed in a future version.
     */
    public Link getCaptureLink() {
        return getLink("capture");
    }

    /**
     * @deprecated Will be removed in a future version.
     */
    public boolean canVoid() {
        return getLinks().containsKey("void");
    }

    /**
     * @deprecated Will be removed in a future version.
     */
    public Link getVoidLink() {
        return getLink("void");
    }

}
