package com.checkout.sessions;

import com.checkout.common.ChallengeIndicator;
import com.checkout.common.Currency;
import com.checkout.common.Resource;
import com.checkout.common.ThreeDSFlowType;
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

    private String sessionSecret;

    private String transactionId;

    private SessionScheme scheme;

    private Long amount;

    private Currency currency;

    private Boolean completed;

    private Boolean challenged;

    private AuthenticationType authenticationType;

    private Category authenticationCategory;

    private DsPublicKeys certificates;

    private SessionStatus status;

    private StatusReason statusReason;

    private Boolean approved;

    private String protocolVersion;

    @SerializedName("account_info")
    private CardholderAccountInfo cardholderAccountInfo;

    private MerchantRiskInfo merchantRiskInfo;

    private String reference;

    private TransactionType transactionType;

    private List<NextAction> nextActions;

    private Ds ds;

    private Acs acs;

    private ResponseCode responseCode;

    private String responseStatusReason;

    private String pareq;

    private String cryptogram;

    private String eci;

    private String xid;

    private String cardholderInfo;

    private CardInfo card;

    private Recurring recurring;

    private Installment installment;

    private InitialTransaction initialTransaction;

    private String customerIp;

    private Instant authenticationDate;

    private ThreeDSExemption exemption;

    private ThreeDSFlowType flowType;

    private ChallengeIndicator challengeIndicator;

    private Optimization optimization;

    private SchemeInfo schemeInfo;

}
