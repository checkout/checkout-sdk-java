package com.checkout.payments.contexts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentContextsPartnerMetadata {

    private String orderId;

    private String customerId;

    private String sessionId;

    private String clientToken;
}
