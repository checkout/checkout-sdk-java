package com.checkout.sessions;

import com.checkout.common.Currency;
import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GetSessionResponseAfterChannelDataSupplied extends Resource {

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

    private String transactionType;

    private List<NextAction> nextActions;

    private Ds ds;

    private Acs acs;

    private ResponseCode responseCode;

    private String responseStatusReason;

    private String pareq;

    private String cryptogram;

    private String eci;

    private String xid;

    private CardInfo card;

}
