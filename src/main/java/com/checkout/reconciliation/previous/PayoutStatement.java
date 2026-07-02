package com.checkout.reconciliation.previous;

import com.checkout.common.Currency;
import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PayoutStatement extends Resource {

    private String id;

    private String status;

    private Currency currency;

    private String payoutFee;

    private String netAmount;

    private String carriedForwardAmount;

    private String currentPeriodAmount;

    private String date;

    private String periodStart;

    private String periodEnd;
    
    private CurrentPeriodBreakdown currentPeriodBreakdown;

}
