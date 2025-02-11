package com.checkout.payments.contexts;

import com.checkout.common.CustomerRequest;
import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PaymentContextsCustomerRequest extends CustomerRequest {

    @SerializedName("email_verified")
    private Boolean emailVerified;

    private PaymentContextsCustomerSummary summary;

    @Builder
    private PaymentContextsCustomerRequest(final String email,
                                           final String name,
                                           final Phone phone,
                                           final Boolean emailVerified,
                                           final PaymentContextsCustomerSummary summary) {
        super(email, name, phone);
        this.emailVerified = emailVerified;
        this.summary = summary;
    }
}
