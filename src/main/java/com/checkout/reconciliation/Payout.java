package com.checkout.reconciliation;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Payout extends Resource {
    private String id;
    private String status;
    private String currency;
    private String payoutFee;
    private String netAmount;
    private String carriedForwardAmount;
    private String currentPeriodAmount;
    private Instant date;
    private Instant periodStart;
    private Instant periodEnd;
}
