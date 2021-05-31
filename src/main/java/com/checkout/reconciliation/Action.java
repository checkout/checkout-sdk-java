package com.checkout.reconciliation;


import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class Action {
    String type;
    String id;
    Instant processedOn;
    String responseCode;
    String responseDescription;
    List<Breakdown> breakdowns = new ArrayList<>();
}
