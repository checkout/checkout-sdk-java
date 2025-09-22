package com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.instruction;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * instruction
 * Additional details about the unreferenced refund instruction.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Instruction {

    /**
     * Required if processing.processing_speed is fast.
     * Value of this field should be based on the destination card scheme:
     * For Visa, use MI
     * For Mastercard, use C60
     * [Optional]
     */
    @SerializedName("funds_transfer_type")
    private String fundsTransferType;

    /**
     * The purpose of the unreferenced refund.
     * This field is required if the card's issuer_country is one of:
     * AR (Argentina)
     * BD (Bangladesh)
     * CL (Chile)
     * CO (Colombia)
     * EG (Egypt)
     * IN (India)
     * MX (Mexico)
     * To view a card's issuer_country, retrieve the card's metadata.
     * [Optional]
     */
    private PurposeType purpose;

}
