package com.checkout.payments.four.request;

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

    private Boolean repair;

    private InstructionScheme scheme;

    @SerializedName("quote_id")
    private String quoteId;

    //Beta
    @SerializedName("skip_expiry")
    private Boolean skipExpiry;

    @SerializedName("funds_transfer_type")
    private String fundsTransferType;

    private String mvv;

}