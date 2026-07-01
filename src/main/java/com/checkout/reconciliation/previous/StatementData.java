package com.checkout.reconciliation.previous;


import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class StatementData extends Resource {

    private String id;

    private String periodStart;

    private String periodEnd;

    private String date;

    private List<PayoutStatement> payouts;

}
