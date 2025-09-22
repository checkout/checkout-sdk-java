package com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponseaccepted.destination.instruction;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Instruction {

    /**
     * The date (in ISO 8601 format) and time at which the recipient's account will be credited.
     * [Optional]
     */
    @SerializedName("value_date")
    private Instant valueDate;

}
