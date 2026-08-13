package com.checkout.payments.response;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class PaymentInstructionResponse {

    private Instant valueDate;

    /**
     * The scheme's categorisation of the client, for example {@code FD}, {@code MT} or {@code AA}.
     */
    @SerializedName("funds_transfer_type")
    private String fundsTransferType;

}