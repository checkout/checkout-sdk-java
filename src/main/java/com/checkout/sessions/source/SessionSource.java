package com.checkout.sessions.source;

import com.checkout.common.Phone;
import com.checkout.sessions.SessionAddress;
import com.checkout.sessions.SessionScheme;
import com.checkout.sessions.SessionSourceType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class SessionSource {

    protected final SessionSourceType type;

    private SessionScheme scheme;

    protected SessionAddress billingAddress;

    protected Phone homePhone;

    protected Phone mobilePhone;

    protected Phone workPhone;

    protected String email;

    protected SessionSource(final SessionSourceType type) {
        this.type = type;
    }

}
