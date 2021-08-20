package com.checkout.reconciliation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
public final class ReconciliationQueryPaymentsFilter {

    private String from;

    private String to;

    private String reference;

    @Size(max = 500)
    private Integer limit;

}
