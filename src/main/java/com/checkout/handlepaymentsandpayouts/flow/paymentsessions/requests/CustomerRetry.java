package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CustomerRetry {

    /**
     * Default: 5
     * The maximum number of authorization retry attempts, excluding the initial authorization.
     */
    @Builder.Default
    @SerializedName("max_attempts")
    private Long maxAttempts = 5L;

}
