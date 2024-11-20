package com.checkout.accounts;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class Capabilities {

    private Payments payments;

    private Payouts payouts;

    private IssuingCapabilities issuing;

    @Data
    public static class Payments {

        private Boolean available;

        private Boolean enabled;

    }

    @Data
    public static class Payouts {

        private Boolean available;

        private Boolean enabled;

    }

    @Data
    public static class IssuingCapabilities {

        private Boolean available;

        private Boolean enabled;

    }

}
