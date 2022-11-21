package com.checkout.metadata.card;

import com.checkout.metadata.card.source.CardMetadataRequestSource;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class CardMetadataRequest {

    private CardMetadataRequestSource source;

    private CardMetadataFormatType format;

}
