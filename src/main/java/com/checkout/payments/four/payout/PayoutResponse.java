package com.checkout.payments.four.payout;

import com.checkout.common.Resource;
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

    private PaymentInstructionResponse instruction;

}
