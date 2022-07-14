package com.checkout.reconciliation.previous;


import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class StatementData extends Resource {

    private String id;

    @SerializedName("period_start")
    private String periodStart;

    @SerializedName("period_end")
    private String periodEnd;

    private String date;

    private List<PayoutStatement> payouts;

}
