package com.checkout.reconciliation;


import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StatementData extends Resource {
    private String id;
    private Instant periodStart;
    private Instant periodEnd;
    private Instant date;
    private List<Payout> payouts = new ArrayList<>();

}
