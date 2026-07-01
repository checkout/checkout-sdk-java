package com.checkout.payments.hosted;

import com.checkout.common.AmountAllocations;
import com.checkout.common.Currency;
import com.checkout.common.CustomerResponse;
import com.checkout.common.Resource;
import com.checkout.common.Product;
import com.checkout.payments.BillingInformation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class HostedPaymentDetailsResponse extends Resource {

    private String id;

    private HostedPaymentStatus status;

    private Long amount;

    private Currency currency;

    private BillingInformation billing;

    private String successUrl;

    private String cancelUrl;

    private String failureUrl;

    private String paymentId;

    private String reference;

    private String description;

    private CustomerResponse customer;


    private List<Product> products;

    private Map<String, Object> metadata;

    private List<AmountAllocations> amountAllocations;

}
