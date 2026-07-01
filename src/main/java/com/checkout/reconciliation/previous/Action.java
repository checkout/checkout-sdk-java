package com.checkout.reconciliation.previous;


import lombok.Data;

import java.util.List;

@Data
public final class Action {

    private String type;

    private String id;

    private String processedOn;

    private String responseCode;

    private String responseDescription;

    private List<Breakdown> breakdown;

}
