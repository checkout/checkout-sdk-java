package com.checkout.issuing.cards.requests.renew;

import com.checkout.issuing.cards.CardType;
import com.checkout.issuing.cards.requests.update.CardMetadata;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public abstract class RenewCardRequest {

    private final CardType type;

    @SerializedName("display_name")
    private String displayName;

    private String reference;

    private CardMetadata metadata;

    protected RenewCardRequest(final CardType type,
                              final String displayName,
                              final String reference,
                              final CardMetadata metadata) {
        this.type = type;
        this.displayName = displayName;
        this.reference = reference;
        this.metadata = metadata;
    }
}