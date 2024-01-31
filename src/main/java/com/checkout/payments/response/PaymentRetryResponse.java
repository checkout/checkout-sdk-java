package com.checkout.payments.response;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public final class PaymentRetryResponse {

    @SerializedName("max_attempts")
    private Integer maxAttempts;

    @SerializedName("ends_on")
    private Instant endsOn;

    @SerializedName("next_attempt_on")
    private Instant nextAttemptOn;

}
