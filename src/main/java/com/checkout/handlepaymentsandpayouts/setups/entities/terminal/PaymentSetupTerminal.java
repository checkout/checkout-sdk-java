package com.checkout.handlepaymentsandpayouts.setups.entities.terminal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Terminal details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentSetupTerminal {

    /**
     * Terminal identifier.
     * [Optional]
     * min 8 characters, max 8 characters
     */
    private String id;

    /**
     * The local date and time on the terminal, in ISO 8601 format.
     * [Optional]
     * Format: date-time (RFC 3339)
     */
    private Instant localDateTime;
}
