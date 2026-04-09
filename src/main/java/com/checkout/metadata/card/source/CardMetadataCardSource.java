package com.checkout.metadata.card.source;

import com.checkout.metadata.card.CardMetadataSourceType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Card metadata source identified by a full Primary Account Number (PAN).
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class CardMetadataCardSource extends CardMetadataRequestSource {

    /**
     * The Primary Account Number (PAN).
     * [Required]
     * >= 12 characters
     * <= 19 characters
     * Pattern: ^[0-9]+$
     */
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
