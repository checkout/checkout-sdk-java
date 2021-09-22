package com.checkout.apm.klarna;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public final class CreditSessionRequest {

    @SerializedName("purchase_country")
    private CountryCode purchaseCountry;

    private Currency currency;

    private String locale;

    private Integer amount;

    @SerializedName("tax_amount")
    private Integer taxAmount;

    private List<KlarnaProduct> products;

}
