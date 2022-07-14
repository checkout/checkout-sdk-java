package com.checkout.reconciliation.previous;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public final class ReconciliationQueryPaymentsFilter {

    private Instant from;

    private Instant to;

    private String reference;

    @Size(max = 500)
    private Integer limit;

}
