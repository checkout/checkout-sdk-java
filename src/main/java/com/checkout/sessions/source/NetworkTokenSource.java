package com.checkout.sessions.source;

import com.checkout.common.Phone;
import com.checkout.sessions.SessionAddress;
import com.checkout.sessions.SessionScheme;
import com.checkout.sessions.SessionSourceType;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class NetworkTokenSource extends SessionSource {

    private String token;

    @SerializedName("expiry_month")
    private Integer expiryMonth;

    @SerializedName("expiry_year")
    private Integer expiryYear;

    private String name;

    private Boolean stored;

    @Builder
    private NetworkTokenSource(final String token,
                               final Integer expiryMonth,
                               final Integer expiryYear,
                               final String name,
                               final String email,
                               final SessionScheme scheme,
                               final SessionAddress billingAddress,
                               final Phone homePhone,
                               final Phone mobilePhone,
                               final Phone workPhone,
                               final Boolean stored) {
        super(SessionSourceType.NETWORK_TOKEN, scheme, billingAddress, homePhone, mobilePhone, workPhone, email);
        this.token = token;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.name = name;
        this.stored = stored;
    }

    public NetworkTokenSource() {
        super(SessionSourceType.NETWORK_TOKEN);
    }
}
