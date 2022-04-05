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
public final class SessionsRequestIdSource extends SessionSource {

    private String id;

    @Builder
    private SessionsRequestIdSource(final String id,
                                    final SessionAddress billingAddress,
                                    final Phone homePhone,
                                    final Phone mobilePhone,
                                    final Phone workPhone,
                                    final SessionScheme scheme) {
        super(SessionSourceType.ID, billingAddress, homePhone, mobilePhone, workPhone, scheme);
        this.id = id;
    }

    public SessionsRequestIdSource() {
        super(SessionSourceType.ID);
    }

}
