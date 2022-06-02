package com.checkout.sessions.source;

import com.checkout.common.Phone;
import com.checkout.sessions.SessionAddress;
import com.checkout.sessions.SessionSourceType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestTokenSource extends SessionSource {

    private String token;

    @Builder
    private RequestTokenSource(final String token,
                               final SessionAddress billingAddress,
                               final Phone homePhone,
                               final Phone mobilePhone,
                               final Phone workPhone,
                               final String email) {
        super(SessionSourceType.TOKEN, billingAddress, homePhone, mobilePhone, workPhone, email);
        this.token = token;
    }

    public RequestTokenSource() {
        super(SessionSourceType.TOKEN);
    }

}
