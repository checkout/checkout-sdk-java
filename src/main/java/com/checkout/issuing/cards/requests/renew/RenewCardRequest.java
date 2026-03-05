package com.checkout.issuing.cards.requests.renew;

import com.checkout.issuing.cards.requests.update.IssuingCardMetadata;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public abstract class RenewCardRequest {

    @SerializedName("display_name")
    private String displayName;

    private String reference;

    private IssuingCardMetadata metadata;

    protected RenewCardRequest(final String displayName,
                              final String reference,
                              final IssuingCardMetadata metadata) {
        this.displayName = displayName;
        this.reference = reference;
        this.metadata = metadata;
    }
}