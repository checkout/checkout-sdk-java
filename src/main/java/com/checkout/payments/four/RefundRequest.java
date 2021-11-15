package com.checkout.payments.four;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
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

}
