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
public final class CardMetadataTokenSource extends CardMetadataRequestSource {

    private String token;

    @Builder
    private CardMetadataTokenSource(final String token) {
        super(CardMetadataSourceType.TOKEN);
        this.token = token;
    }

    public CardMetadataTokenSource() {
        super(CardMetadataSourceType.TOKEN);
    }

}
