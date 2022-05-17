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
public class NetworkTokenSource extends SessionSource {

    private String token;

    @SerializedName("expiry_month")
    private Integer expiryMonth;

    @SerializedName("expiry_year")
    private Integer expiryYear;

    private String name;

    private String email;

    @Builder
    private NetworkTokenSource(final String token,
                               final Integer expiryMonth,
                               final Integer expiryYear,
                               final String name,
                               final String email,
                               final SessionAddress billingAddress,
                               final Phone homePhone,
                               final Phone mobilePhone,
                               final Phone workPhone,
                               final SessionScheme scheme) {
        super(SessionSourceType.NETWORK_TOKEN, billingAddress, homePhone, mobilePhone, workPhone, scheme);
        this.token = token;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.name = name;
        this.email = email;
    }

    public NetworkTokenSource() {
        super(SessionSourceType.NETWORK_TOKEN);
    }
}
