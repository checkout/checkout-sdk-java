package com.checkout.apm.previous.klarna;

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

    private Long amount;

    @SerializedName("tax_amount")
    private Long taxAmount;

    private List<KlarnaProduct> products;

}
