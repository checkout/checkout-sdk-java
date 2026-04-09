package com.checkout.metadata.card.source;

import com.checkout.metadata.card.CardMetadataSourceType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Card metadata source identified by a Checkout.com card token.
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class CardMetadataTokenSource extends CardMetadataRequestSource {

    /**
     * The Checkout.com unique token generated when the card's details were tokenized.
     * [Required]
     * Pattern: ^(tok)_(\w{26})$
     */
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
