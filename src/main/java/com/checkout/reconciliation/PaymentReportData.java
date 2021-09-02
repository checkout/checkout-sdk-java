package com.checkout.reconciliation;

import com.checkout.common.CountryCode;
import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentReportData extends Resource {

    private String id;

    private String processingCurrency;

    @SerializedName("payout_currency")
    private String payoutCurrency;

    @SerializedName("requested_on")
    private Instant requestedOn;

    @SerializedName("channel_name")
    private String channelName;

    private String reference;

    @SerializedName("payment_method")
    private String paymentMethod;

    @SerializedName("card_type")
    private String cardType;

    @SerializedName("card_category")
    private String cardCategory;

    @SerializedName("issuer_country")
    private CountryCode issuerCountry;

    @SerializedName("merchant_country")
    private CountryCode merchantCountry;

    private String mid;

    private List<Action> actions;

    /**
     * @deprecated Will be removed on a future version
     */
    @Deprecated
    public String getIssuerCountry() {
        return Optional.ofNullable(issuerCountry).map(CountryCode::name).orElse(null);
    }

    /**
     * @deprecated Will be removed on a future version
     */
    @Deprecated
    public void setIssuerCountry(final String issuerCountry) {
        this.issuerCountry = CountryCode.fromString(issuerCountry);
    }

    public void setIssuerCountry(final CountryCode issuerCountry) {
        this.issuerCountry = issuerCountry;
    }

    /**
     * @deprecated Will be removed on a future version
     */
    @Deprecated
    public String getMerchantCountry() {
        return Optional.ofNullable(issuerCountry).map(CountryCode::name).orElse(null);
    }

    /**
     * @deprecated Will be removed on a future version
     */
    @Deprecated
    public void setMerchantCountry(final String merchantCountry) {
        this.merchantCountry = CountryCode.fromString(merchantCountry);
    }

    public void setMerchantCountry(final CountryCode merchantCountry) {
        this.merchantCountry = merchantCountry;
    }

}
