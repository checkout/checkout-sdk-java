package com.checkout.reconciliation;

import com.checkout.common.Currency;
import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PayoutStatement extends Resource {

    private String id;

    private String status;

    private Currency currency;

    @SerializedName("payout_fee")
    private String payoutFee;

    @SerializedName("net_amount")
    private String netAmount;

    @SerializedName("carried_forward_amount")
    private String carriedForwardAmount;

    @SerializedName("current_period_amount")
    private String currentPeriodAmount;

    private Instant date;

    @SerializedName("period_start")
    private Instant periodStart;

    @SerializedName("period_end")
    private Instant periodEnd;

}
