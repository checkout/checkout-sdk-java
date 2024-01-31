package com.checkout.payments.response.source;

import com.checkout.common.Address;
import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.CountryCode;
import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CardResponseSource extends AbstractResponseSource implements ResponseSource {

    @SerializedName("billing_address")
    private Address billingAddress;

    private Phone phone;

    @SerializedName("expiry_month")
    private Integer expiryMonth;

    @SerializedName("expiry_year")
    private Integer expiryYear;

    private String name;

    private String scheme;

    /**
     * @deprecated This property will be removed in the future, and should be used
     * {@link CardResponseSource#localSchemes} instead
     */
    @Deprecated
    @SerializedName("scheme_local")
    private String schemeLocal;

    @SerializedName("local_schemes")
    private List<String> localSchemes;

    private String last4;

    private String fingerprint;

    private String bin;

    @SerializedName("card_type")
    private CardType cardType;

    @SerializedName("card_category")
    private CardCategory cardCategory;

    private String issuer;

    @SerializedName("issuer_country")
    private CountryCode issuerCountry;

    @SerializedName("product_id")
    private String productId;

    @SerializedName("product_type")
    private String productType;

    @SerializedName("avs_check")
    private String avsCheck;

    @SerializedName("cvv_check")
    private String cvvCheck;

    @SerializedName("payment_account_reference")
    private String paymentAccountReference;

    @SerializedName("encrypted_card_number")
    private String encryptedCardNumber;

}
