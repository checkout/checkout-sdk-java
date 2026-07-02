package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class Acs {

    private String referenceNumber;

    private String transactionId;

    private String operatorId;

    private String url;

    private String signedContent;

    private Boolean challengeMandated;

    private String authenticationType;

    private ChallengeCancelReason challengeCancelReason;

    @SerializedName("interface")
    private SessionInterface sessionInterface;

    private UIElements uiTemplate;

    private String challengeCancelReasonCode;

}
