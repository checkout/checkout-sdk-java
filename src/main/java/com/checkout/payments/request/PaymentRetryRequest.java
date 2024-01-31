package com.checkout.payments.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class PaymentRetryRequest {

    private Boolean enabled;

    @SerializedName("max_attempts")
    private Integer maxAttempts;

    @SerializedName("end_after_days")
    private Integer endAfterDays;

}
