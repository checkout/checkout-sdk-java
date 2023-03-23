package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class Acs {

    @SerializedName("reference_number")
    private String referenceNumber;

    @SerializedName("transaction_id")
    private String transactionId;

    @SerializedName("operator_id")
    private String operatorId;

    private String url;

    @SerializedName("signed_content")
    private String signedContent;

    @SerializedName("challenge_mandated")
    private Boolean challengeMandated;

    @SerializedName("authentication_type")
    private String authenticationType;

    @SerializedName("challenge_cancel_reason")
    private ChallengeCancelReason challengeCancelReason;

    @SerializedName("interface")
    private SessionInterface sessionInterface;

    @SerializedName("ui_template")
    private UIElements uiTemplate;

    @SerializedName("challenge_cancel_reason_code")
    private String challengeCancelReasonCode;

}
