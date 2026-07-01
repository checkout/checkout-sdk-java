package com.checkout.sessions.source;

import com.checkout.common.Phone;
import com.checkout.sessions.SessionAddress;
import com.checkout.sessions.SessionScheme;
import com.checkout.sessions.SessionSourceType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class SessionCardSource extends SessionSource {

    private String number;

    private Integer expiryMonth;

    private Integer expiryYear;

    private String name;

    private Boolean stored;

    private Boolean storeForFutureUse;

    @Builder
    private SessionCardSource(final String number,
                              final Integer expiryMonth,
                              final Integer expiryYear,
                              final String name,
                              final Boolean stored,
                              final Boolean storeForFutureUse,
                              final SessionScheme scheme,
                              final SessionAddress billingAddress,
                              final Phone homePhone,
                              final Phone mobilePhone,
                              final Phone workPhone,
                              final String email) {
        super(SessionSourceType.CARD, scheme, billingAddress, homePhone, mobilePhone, workPhone, email);
        this.number = number;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.name = name;
        this.stored = stored != null ? stored : false;;
        this.storeForFutureUse = storeForFutureUse;
    }

    public SessionCardSource() {
        super(SessionSourceType.CARD);
    }

}
