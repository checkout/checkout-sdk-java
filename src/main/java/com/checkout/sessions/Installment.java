package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class Installment {

    @SerializedName("number_of_payments")
    private Long numberOfPayments;

    @Builder.Default
    @SerializedName("days_between_payments")
    private Long daysBetweenPayments = 1L;

    @Builder.Default
    private String expiry = "99991231";

}
