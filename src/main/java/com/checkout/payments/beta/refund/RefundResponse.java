package com.checkout.payments.beta.refund;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RefundResponse extends Resource {

    private String actionId;

    private String reference;

}