package com.checkout.sessions;

import com.checkout.common.ChallengeIndicator;
import com.checkout.common.Currency;
import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GetSessionResponse extends Resource {

    private String id;

    @SerializedName("session_secret")
    private String sessionSecret;

    @SerializedName("transaction_id")
    private String transactionId;

    private SessionScheme scheme;

    private Long amount;

    private Currency currency;

    private Boolean completed;

    private Boolean challenged;

    @SerializedName("authentication_type")
    private AuthenticationType authenticationType;

    @SerializedName("authentication_category")
    private Category authenticationCategory;

    private DsPublicKeys certificates;

    private SessionStatus status;

    @SerializedName("status_reason")
    private StatusReason statusReason;

    private Boolean approved;

    @SerializedName("protocol_version")
    private String protocolVersion;

    private String reference;

    @SerializedName("transaction_type")
    private TransactionType transactionType;

    @SerializedName("next_actions")
    private List<NextAction> nextActions;

    private Ds ds;

    private Acs acs;

    @SerializedName("response_code")
    private ResponseCode responseCode;

    @SerializedName("response_status_reason")
    private String responseStatusReason;

    private String pareq;

    private String cryptogram;

    private String eci;

    private String xid;

    @SerializedName("cardholder_info")
    private String cardholderInfo;

    private CardInfo card;

    private Recurring recurring;

    @SerializedName("authentication_date")
    private Instant authenticationDate;

    private ThreeDSExemption exemption;

    @SerializedName("flow_type")
    private ThreeDSFlowType flowType;

    @SerializedName("challenge_indicator")
    private ChallengeIndicator challengeIndicator;

    @SerializedName("scheme_info")
    private SchemeInfo schemeInfo;

}
