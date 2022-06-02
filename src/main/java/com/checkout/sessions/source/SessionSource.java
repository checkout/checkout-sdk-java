package com.checkout.sessions.source;

import com.checkout.common.Phone;
import com.checkout.sessions.SessionAddress;
import com.checkout.sessions.SessionSourceType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public abstract class SessionSource {

    protected final SessionSourceType type;

    @SerializedName("billing_address")
    protected SessionAddress billingAddress;

    @SerializedName("home_phone")
    protected Phone homePhone;

    @SerializedName("mobile_phone")
    protected Phone mobilePhone;

    @SerializedName("work_phone")
    protected Phone workPhone;

    protected String email;

    protected SessionSource(final SessionSourceType type,
                            final SessionAddress billingAddress,
                            final Phone homePhone,
                            final Phone mobilePhone,
                            final Phone workPhone,
                            final String email) {
        this.type = type;
        this.billingAddress = billingAddress;
        this.homePhone = homePhone;
        this.mobilePhone = mobilePhone;
        this.workPhone = workPhone;
        this.email = email;
    }

    protected SessionSource(final SessionSourceType type) {
        this.type = type;
    }

}
