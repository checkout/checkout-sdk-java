package com.checkout.issuing.cards.requests.update;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}