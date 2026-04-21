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

    /**
     * Configuration of asynchronous Dunning retries.
     * [Optional]
     */
    private DunningRetryRequest dunning;

    /**
     * Configuration of asynchronous Downtime retries.
     * [Optional]
     */
    private DowntimeRetryRequest downtime;

    // ========================================
    // Deprecated — use dunning.enabled instead
    // ========================================

    /**
     * @deprecated Use {@link DunningRetryRequest#enabled} via {@link #dunning} instead.
     */
    @Deprecated
    private Boolean enabled;

    /**
     * @deprecated Use {@link DunningRetryRequest#maxAttempts} via {@link #dunning} instead.
     */
    @Deprecated
    @SerializedName("max_attempts")
    private Integer maxAttempts;

    /**
     * @deprecated Use {@link DunningRetryRequest#endAfterDays} via {@link #dunning} instead.
     */
    @Deprecated
    @SerializedName("end_after_days")
    private Integer endAfterDays;

}
