package com.checkout.apm.previous.klarna;

import com.checkout.common.PaymentSourceType;
import com.checkout.common.ShippingInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
public final class OrderCaptureRequest {

    private final PaymentSourceType type = PaymentSourceType.KLARNA;

    private Long amount;

    private String reference;

    private Map<String, Object> metadata;

    private Klarna klarna;

    private ShippingInfo shippingInfo;

    private Long shippingDelay;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Klarna {

        private String description;

        private List<KlarnaProduct> products;

        private List<ShippingInfo> shippingInfo;

        private Integer shippingDelay;

    }
}
