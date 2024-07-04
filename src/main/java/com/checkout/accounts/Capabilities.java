package com.checkout.accounts;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class Capabilities {

    private Payments payments;

    private Payouts payouts;

    private Issuing issuing;

    @Data
    public static class Payments {

        private boolean available;

        private boolean enabled;

    }

    @Data
    public static class Payouts {

        private boolean available;

        private boolean enabled;

    }

    @Data
    public static class Issuing {

        private boolean available;

        private boolean enabled;

    }

}
