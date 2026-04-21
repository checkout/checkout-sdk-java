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
public final class DunningRetryRequest {

    /**
     * Whether dunning retries are enabled.
     * [Optional]
     */
    private Boolean enabled;

    /**
     * The maximum number of retry attempts.
     * [Optional]
     */
    @SerializedName("max_attempts")
    private Integer maxAttempts;

    /**
     * The number of days after which retries stop.
     * [Optional]
     */
    @SerializedName("end_after_days")
    private Integer endAfterDays;

}
