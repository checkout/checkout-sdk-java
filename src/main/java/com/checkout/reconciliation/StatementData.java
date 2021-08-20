package com.checkout.reconciliation;


import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class StatementData extends Resource {

    private String id;

    @SerializedName("period_start")
    private Instant periodStart;

    @SerializedName("period_end")
    private Instant periodEnd;

    private Instant date;

    private List<PayoutStatement> payouts;

}
