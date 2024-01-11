package com.checkout.payments.sessions;

import com.checkout.common.Currency;
import com.checkout.common.CustomerResponse;
import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentSessionsResponse extends Resource {

    private String id;

    private Long amount;

    private String locale;

    private Currency currency;

    private CustomerResponse customer;

    @SerializedName("payment_methods")
    private List<PaymentMethods> paymentMethods;

}
