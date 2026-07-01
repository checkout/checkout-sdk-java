package com.checkout.payments;

import com.checkout.common.AmountAllocations;
import com.checkout.common.Destination;
import com.checkout.payments.request.RefundOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class RefundRequest {

    private Long amount;

    private String reference;

    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();

    // Not available on Previous
    private List<AmountAllocations> amountAllocations;

    private String captureActionId;

    private Destination destination;

    private List<RefundOrder> items;

}