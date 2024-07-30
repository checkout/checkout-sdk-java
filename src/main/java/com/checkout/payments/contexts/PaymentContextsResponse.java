package com.checkout.payments.contexts;

import com.checkout.payments.response.source.contexts.ResponseSource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentContextsResponse extends PaymentContexts {

    private ResponseSource source;

}
