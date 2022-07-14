package com.checkout.payments.request;

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
public final class AuthorizationRequest {

    private Long amount;

    private String reference;

    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();

}
