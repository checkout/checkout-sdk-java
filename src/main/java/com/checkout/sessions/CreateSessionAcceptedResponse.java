package com.checkout.sessions;

import com.checkout.common.ChallengeIndicator;
import com.checkout.common.Currency;
import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreateSessionAcceptedResponse extends Resource {

    private String id;

    private String sessionSecret;

    private String transactionId;

    private SessionScheme scheme;

    private Long amount;

    private Currency currency;

    private AuthenticationType authenticationType;

    private Category authenticationCategory;

    private SessionStatus status;

    private StatusReason statusReason;

    private List<NextAction> nextActions;

    private String protocolVersion;

    private CardholderAccountInfo accountInfo;

    private MerchantRiskInfo merchantRiskInfo;

    private String reference;

    private CardInfo card;

    private Recurring recurring;

    private Installment installment;

    private InitialTransaction initialTransaction;

    private Instant authenticationDate;

    private ChallengeIndicator challengeIndicator;

    private Optimization optimization;

}
