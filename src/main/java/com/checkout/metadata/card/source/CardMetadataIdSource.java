package com.checkout.metadata.card.source;

import com.checkout.metadata.card.CardMetadataSourceType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Card metadata source identified by a Checkout.com payment instrument ID.
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class CardMetadataIdSource extends CardMetadataRequestSource {

    /**
     * The unique ID for the payment instrument created using the card's details.
     * [Required]
     * Pattern: ^(src)_(\w{26})$
     */
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
