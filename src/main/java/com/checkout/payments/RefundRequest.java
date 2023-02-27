package com.checkout.payments;

import com.checkout.common.AmountAllocations;
import com.google.gson.annotations.SerializedName;
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

    @SerializedName("amount_allocations")
    private List<AmountAllocations> amountAllocations;

    private String reference;

    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();

}