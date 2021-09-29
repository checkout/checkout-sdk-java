package com.checkout.sessions.source;

import com.checkout.common.Phone;
import com.checkout.sessions.SessionAddress;
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

    private String email;

    @Builder
    private SessionCardSource(final String number,
                              final Integer expiryMonth,
                              final Integer expiryYear,
                              final String name,
                              final String email,
                              final SessionAddress billingAddress,
                              final Phone homePhone,
                              final Phone mobilePhone,
                              final Phone workPhone) {
        super(SessionSourceType.CARD, billingAddress, homePhone, mobilePhone, workPhone);
        this.number = number;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.name = name;
        this.email = email;
    }

    public SessionCardSource() {
        super(SessionSourceType.CARD);
    }

}
