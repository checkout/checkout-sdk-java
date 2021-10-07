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
public final class CreateSessionAcceptedResponse extends Resource {

    private String id;

    @SerializedName("session_secret")
    private String sessionSecret;

    @SerializedName("transaction_id")
    private String transactionId;

    private SessionScheme scheme;

    private Long amount;

    private Currency currency;

    @SerializedName("authentication_type")
    private AuthenticationType authenticationType;

    @SerializedName("authentication_category")
    private Category authenticationCategory;

    private SessionStatus status;

    private StatusReason reason;

    @SerializedName("next_actions")
    private List<NextAction> nextActions;

    @SerializedName("protocol_version")
    private String protocolVersion;

    private String reference;

    private CardInfo card;

}
