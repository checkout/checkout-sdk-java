package com.checkout.marketplace;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class Capabilities {

    private Payments payments;

    private Payouts payouts;

    @Data
    public static class Payments {

        private boolean enabled;

    }

    @Data
    public static class Payouts {

        private boolean enabled;

    }

}
