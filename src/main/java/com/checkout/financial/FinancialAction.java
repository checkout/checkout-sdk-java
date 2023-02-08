package com.checkout.financial;

import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
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
public class FinancialAction {

    @SerializedName("payment_id")
    private String paymentId;

    @SerializedName("action_id")
    private String actionId;

    @SerializedName("action_type")
    private String actionType;

    @SerializedName("entity_id")
    private String entityId;

    @SerializedName("sub_entity_id")
    private String subEntityId;

    @SerializedName("currency_account_id")
    private String currencyAccountId;

    @SerializedName("payment_method")
    private String paymentMethod;

    @SerializedName("processing_channel_id")
    private String processingChannelId;

    private String reference;

    private String mid;

    @SerializedName("response_code")
    private String responseCode;

    @SerializedName("response_description")
    private String responseDescription;

    private Region region;

    @SerializedName("card_type")
    private CardType cardType;

    @SerializedName("card_category")
    private CardCategory cardCategory;

    @SerializedName("issuer_country")
    private CountryCode issuerCountry;

    @SerializedName("merchant_category_code")
    private String merchantCategoryCode;

    @SerializedName("fx_trade_id")
    private String fxTradeId;

    @SerializedName("processed_on")
    private Instant processedOn;

    @SerializedName("requested_on")
    private Instant requestedOn;

    private List<ActionBreakdown> breakdown;
}
