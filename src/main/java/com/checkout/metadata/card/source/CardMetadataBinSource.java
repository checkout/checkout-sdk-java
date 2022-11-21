package com.checkout.metadata.card.source;

import com.checkout.metadata.card.CardMetadataSourceType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class CardMetadataBinSource extends CardMetadataRequestSource {

    private String bin;

    @Builder
    private CardMetadataBinSource(final String bin) {
        super(CardMetadataSourceType.BIN);
        this.bin = bin;
    }

    public CardMetadataBinSource() {
        super(CardMetadataSourceType.BIN);
    }

}
