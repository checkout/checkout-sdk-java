package com.checkout.issuing.cardholders;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public final class CardholderRequest extends CardholderUpdateRequest {

    private CardholderType type;

    private String reference;

    @SerializedName("entity_id")
    private String entityId;
}
