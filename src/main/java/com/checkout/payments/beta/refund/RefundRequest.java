package com.checkout.payments.beta.refund;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class RefundRequest {

    private Long amount;

    private String reference;

    private Map<String, Object> metadata;

}