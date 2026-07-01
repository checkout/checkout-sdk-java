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

    private String reference;

    private IssuingCardMetadata metadata;

    private Integer expiryMonth;

    private Integer expiryYear;

    /**
     * ISO 8601 date scheduling the card's activation. Two formats are supported:
     * date only (YYYY-MM-DD, treated as midnight UTC), or date with round hour
     * (YYYY-MM-DDTHH:mmZ in UTC, or YYYY-MM-DDTHH:mm±HH:mm with offset). Only round hours are
     * allowed when a time is provided (HH:00). The value must be at least the next round hour
     * after the request time.
     * <p>
     * [Optional]
     * </p>
     */
    private String activationDate;

    /**
     * Date for the card to be automatically revoked. Must be after the current date.
     * <p>
     * [Optional]
     * </p>
     * Format: yyyy-MM-dd
     */
    private LocalDate revocationDate;
}