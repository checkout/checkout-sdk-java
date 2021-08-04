package com.checkout.payments.links;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public final class PaymentLinkResponse extends Resource {
    private String id;
    private String expiresOn;
    private String reference;
}
