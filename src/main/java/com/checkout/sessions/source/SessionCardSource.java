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
public final class SessionCardSource extends SessionSource {

    private String number;

    @SerializedName("expiry_month")
    private Integer expiryMonth;

    @SerializedName("expiry_year")
    private Integer expiryYear;

    private String name;

    private SessionScheme scheme;

    private Boolean stored;

    @Builder
    private SessionCardSource(final String number,
                              final Integer expiryMonth,
                              final Integer expiryYear,
                              final String name,
                              final String email,
                              final SessionAddress billingAddress,
                              final Phone homePhone,
                              final Phone mobilePhone,
                              final Phone workPhone,
                              final SessionScheme scheme,
                              final Boolean stored) {
        super(SessionSourceType.CARD, billingAddress, homePhone, mobilePhone, workPhone, email);
        this.number = number;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.name = name;
        this.scheme = scheme;
        this.stored = stored;
    }

    public SessionCardSource() {
        super(SessionSourceType.CARD);
    }

}
