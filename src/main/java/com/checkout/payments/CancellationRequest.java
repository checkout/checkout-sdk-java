package com.checkout.payments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request body for cancelling an upcoming scheduled payment retry.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CancellationRequest {

    /**
     * A reference you can later use to identify this cancellation request.
     */
    private String reference;

}
