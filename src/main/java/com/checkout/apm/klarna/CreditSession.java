package com.checkout.apm.klarna;

import com.checkout.common.CountryCode;
import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreditSession extends Resource {

    @SerializedName("client_token")
    private String clientToken;

    @SerializedName("purchase_country")
    private CountryCode purchaseCountry;

    private String currency;

    private String locale;

    private Integer amount;

    @SerializedName("tax_amount")
    private Integer taxAmount;

    private List<KlarnaProduct> products;

}
