package com.checkout.payments.four.payout;

import com.checkout.payments.four.ChargeBearer;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class PaymentInstruction {

    private String purpose;

    @SerializedName("charge_bearer")
    private ChargeBearer chargeBearer;

    private boolean repair;

    private InstructionScheme scheme;

    @SerializedName("quote_id")
    private String quoteId;

}