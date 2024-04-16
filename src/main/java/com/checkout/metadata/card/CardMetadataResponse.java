package com.checkout.metadata.card;

import com.checkout.HttpMetadata;
import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.SchemeLocalType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class CardMetadataResponse extends HttpMetadata {

    private String bin;

    private String scheme;

    /**
     * @deprecated This property will be removed in the future, and should be used
     * {@link CardMetadataResponse#localSchemes} instead
     */
    @Deprecated
    @SerializedName("scheme_local")
    private SchemeLocalType schemeLocal;

    @SerializedName("local_schemes")
    private SchemeLocalType localSchemes;

    @SerializedName("card_type")
    private CardType cardType;

    @SerializedName("card_category")
    private CardCategory cardCategory;

    private Currency currency;

    private String issuer;

    @SerializedName("issuer_country")
    private CountryCode issuerCountry;

    @SerializedName("issuer_country_name")
    private String issuerCountryName;

    @SerializedName("product_id")
    private String productId;

    @SerializedName("product_type")
    private String productType;

    @SerializedName("subproduct_id")
    private String subproductId;

    @SerializedName("regulated_indicator")
    private Boolean regulatedIndicator;

    @SerializedName("regulated_type")
    private String regulatedType;

    @SerializedName("card_payouts")
    private CardMetadataPayouts payouts;

    @SerializedName("scheme_metadata")
    private SchemeMetadata schemeMetadata;
}
