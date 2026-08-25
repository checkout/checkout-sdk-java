package com.checkout.payments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class VoidRequest {

    /**
     * The amount to void. If not specified, the full payment amount is voided.
     * Integer, min 0, max 9999999999.
     */
    private Long amount;

    private String reference;

    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();

}
