package com.checkout.payments.beta.payout;

import com.checkout.common.beta.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PayoutResponse extends Resource {

    private String id;

    private String status;

    private String reference;

    private PaymentInstruction instruction;

}
