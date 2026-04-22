package com.checkout.payments.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class DowntimeRetryRequest {

    /**
     * Whether downtime retries are enabled.
     * [Optional]
     */
    private Boolean enabled;

}
