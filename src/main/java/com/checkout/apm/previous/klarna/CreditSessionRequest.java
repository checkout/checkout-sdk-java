package com.checkout.apm.previous.klarna;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public final class CreditSessionRequest {

    private CountryCode purchaseCountry;

    private Currency currency;

    private String locale;

    private Long amount;

    private Long taxAmount;

    private List<KlarnaProduct> products;

}
