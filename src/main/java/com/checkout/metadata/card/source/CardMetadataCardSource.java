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
public final class CardMetadataCardSource extends CardMetadataRequestSource {

    private String number;

    @Builder
    private CardMetadataCardSource(final String number) {
        super(CardMetadataSourceType.CARD);
        this.number = number;
    }

    public CardMetadataCardSource() {
        super(CardMetadataSourceType.CARD);
    }

}
