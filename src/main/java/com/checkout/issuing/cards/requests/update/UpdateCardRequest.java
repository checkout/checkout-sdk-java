package com.checkout.issuing.cards.requests.update;

import com.google.gson.annotations.SerializedName;
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

    @SerializedName("expiry_month")
    private Integer expiryMonth;

    @SerializedName("expiry_year")
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
    @SerializedName("activation_date")
    private String activationDate;

    /**
     * Date for the card to be automatically revoked. Must be after the current date.
     * <p>
     * [Optional]
     * </p>
     * Format: yyyy-MM-dd
     */
    @SerializedName("revocation_date")
    private LocalDate revocationDate;
}