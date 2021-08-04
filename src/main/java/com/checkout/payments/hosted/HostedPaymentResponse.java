package com.checkout.payments.hosted;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public final class HostedPaymentResponse extends Resource {
    private String reference;
}
