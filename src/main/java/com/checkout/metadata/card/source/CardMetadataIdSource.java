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
public final class CardMetadataIdSource extends CardMetadataRequestSource {

    private String id;

    @Builder
    private CardMetadataIdSource(final String id) {
        super(CardMetadataSourceType.ID);
        this.id = id;
    }

    public CardMetadataIdSource() {
        super(CardMetadataSourceType.ID);
    }

}
