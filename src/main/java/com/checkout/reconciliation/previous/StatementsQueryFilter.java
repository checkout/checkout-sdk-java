package com.checkout.reconciliation.previous;

import com.checkout.common.Currency;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public final class StatementsQueryFilter {

    private String payoutId;

    private Currency payoutCurrency;

    private Instant from;

    private Instant to;

    private IncludePayouts include;
}
