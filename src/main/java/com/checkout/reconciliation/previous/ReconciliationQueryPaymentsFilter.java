package com.checkout.reconciliation.previous;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public final class ReconciliationQueryPaymentsFilter {

    private Instant from;

    private Instant to;

    private String reference;

    @Builder.Default
    @Size(max = 500)
    private Integer limit = 500;

}
