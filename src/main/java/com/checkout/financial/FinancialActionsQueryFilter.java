package com.checkout.financial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class FinancialActionsQueryFilter {

    private String paymentId;

    private String actionId;

    private String reference;

    @Size(min = 1, max = 100)
    private Integer limit;

    private String paginationToken;
}
