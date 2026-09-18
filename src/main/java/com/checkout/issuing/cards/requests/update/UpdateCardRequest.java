package com.checkout.issuing.cards.requests.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateCardRequest {

    /**
     * Your reference.
     * [Optional]
     * max 256 characters
     */
    private String reference;

    /**
     * User's metadata.
     * [Optional]
     */
    private IssuingCardMetadata metadata;

    /**
     * The card's expiration month.
     * [Optional]
     * min 1, max 12
     */
    private Integer expiryMonth;

    /**
     * The card's expiration year.
     * [Optional]
     * min 4 characters, max 4 characters
     */
    private Integer expiryYear;

    /**
     * Date scheduling the card's first activation. Only applies to the initial activation of a
     * card. Two formats are supported: date only (YYYY-MM-DD, treated as midnight UTC), or date
     * with round hour (YYYY-MM-DDTHH:mmZ in UTC, or YYYY-MM-DDTHH:mm+HH:mm with offset). Only
     * round hours are allowed when a time is provided (HH:00). The value must be at least the next
     * round hour after the request time.
     * <p>
     * [Optional]
     * </p>
     * Example: 2026-06-01T10:00Z
     */
    private String scheduledActivationDate;

    /**
     * Date scheduling the card's automatic revocation.
     * <p>
     * [Optional]
     * </p>
     * Format: date (YYYY-MM-DD, time is midnight UTC)
     * Example: 2027-03-12
     */
    private LocalDate revocationDate;
}