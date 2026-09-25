package com.checkout.issuing.cards.requests.update;

import com.checkout.issuing.cards.CardStatus;
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
     * Set the card's status to {@code ACTIVE} to reactivate an {@code INACTIVE} or
     * {@code SUSPENDED} card. The API only accepts {@code active} for this field.
     * <p>
     * [Optional]
     * </p>
     * Mutually exclusive with {@link #scheduledActivationDate}: submitting both results in the
     * API error {@code scheduled_activation_date_conflicts_with_activation}.
     */
    private CardStatus status;

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
     * Format: yyyy-MM-dd
     *
     * @deprecated Use {@link #scheduledRevocationDate} instead. If both fields are provided,
     * {@code scheduledRevocationDate} overrides this value.
     */
    @Deprecated
    private LocalDate revocationDate;

    /**
     * The card will be revoked at midnight UTC on the date specified.
     * <p>
     * [Optional]
     * </p>
     * Format: yyyy-MM-dd
     */
    private LocalDate scheduledRevocationDate;
}