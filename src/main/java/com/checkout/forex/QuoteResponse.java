package com.checkout.forex;

import com.checkout.HttpMetadata;
import com.checkout.common.Currency;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
public final class QuoteResponse extends HttpMetadata {

    private String id;

    private Currency sourceCurrency;

    private Long sourceAmount;

    private Currency destinationCurrency;

    private Long destinationAmount;

    private Double rate;

    private Instant expiresOn;

    private boolean isSingleUse;

}
