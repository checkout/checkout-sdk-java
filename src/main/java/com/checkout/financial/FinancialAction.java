package com.checkout.financial;

import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class FinancialAction {

    private String paymentId;

    private String actionId;

    private String actionType;

    private String entityId;

    private String subEntityId;

    private String currencyAccountId;

    private String paymentMethod;

    private String processingChannelId;

    private String reference;

    private String mid;

    private String responseCode;

    private String responseDescription;

    private Region region;

    private CardType cardType;

    private CardCategory cardCategory;

    private CountryCode issuerCountry;

    private String merchantCategoryCode;

    private String fxTradeId;

    private Instant processedOn;

    private Instant requestedOn;

    private List<ActionBreakdown> breakdown;
}
