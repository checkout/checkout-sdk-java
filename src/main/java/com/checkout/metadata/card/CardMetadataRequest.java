package com.checkout.metadata.card;

import com.checkout.metadata.card.source.CardMetadataRequestSource;
import lombok.Builder;
import lombok.Data;

/**
 * Request body for the POST /metadata/card endpoint.
 * Returns a single metadata record for the card specified by PAN, BIN, token, or instrument ID.
 * <p>
 * Beta endpoint — subject to change.
 * </p>
 */
@Data
@Builder
public final class CardMetadataRequest {

    /**
     * The card source to retrieve metadata for.
     * [Required]
     * Supported types: {@link com.checkout.metadata.card.source.CardMetadataCardSource},
     * {@link com.checkout.metadata.card.source.CardMetadataBinSource},
     * {@link com.checkout.metadata.card.source.CardMetadataTokenSource},
     * {@link com.checkout.metadata.card.source.CardMetadataIdSource}
     */
    private CardMetadataRequestSource source;

    /**
     * The format to provide the output in.
     * {@code basic} returns only standard metadata.
     * {@code card_payouts} also includes fields specific to card payouts.
     * [Optional]
     * Default: "basic"
     * Enum: "basic" "card_payouts"
     */
    private CardMetadataFormatType format;

    /**
     * A reference you can later use to identify this request. For example, an order number.
     * [Optional]
     */
    private String reference;

}
