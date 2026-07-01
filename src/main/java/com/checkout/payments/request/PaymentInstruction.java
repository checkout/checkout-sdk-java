package com.checkout.payments.request;

import com.checkout.payments.ChargeBearer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class PaymentInstruction {

    private String purpose;

    private ChargeBearer chargeBearer;

    private Boolean repair;

    private InstructionScheme scheme;

    private String quoteId;

    //Beta
    private Boolean skipExpiry;

    private String fundsTransferType;

    private String mvv;

}