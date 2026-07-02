package com.checkout.apm.previous.klarna;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreditSession extends Resource {

    private String clientToken;

    private String purchaseCountry;

    private String currency;

    private String locale;

    private Long amount;

    private Integer taxAmount;

    private List<KlarnaProduct> products;

}
