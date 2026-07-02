package com.checkout.apm.previous.klarna;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class KlarnaProduct {

    private String name;

    private Long quantity;

    private Long unitPrice;

    private Long taxRate;

    private Long totalAmount;

    private Long totalTaxAmount;

}
