package com.checkout.payments.four.response;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public final class PaymentInstructionResponse {

    @SerializedName("value_date")
    private Instant valueDate;

}