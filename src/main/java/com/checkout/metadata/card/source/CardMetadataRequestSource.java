package com.checkout.metadata.card.source;

import com.checkout.metadata.card.CardMetadataSourceType;
import lombok.Data;

@Data
public abstract class CardMetadataRequestSource {

    protected final CardMetadataSourceType type;

    protected CardMetadataRequestSource(final CardMetadataSourceType type) {
        this.type = type;
    }
}
