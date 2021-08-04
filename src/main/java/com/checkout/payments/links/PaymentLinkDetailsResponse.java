package com.checkout.payments.links;

import com.checkout.common.Product;
import com.checkout.common.Resource;
import com.checkout.payments.BillingInformation;
import com.checkout.payments.CustomerRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public final class PaymentLinkDetailsResponse extends Resource {
    private String id;
    private String status;
    private String expiresOn;
    private String returnUrl;
    private Long amount;
    private String currency;
    private String reference;
    private String description;
    private CustomerRequest customer;
    private BillingInformation billing;
    private List<Product> products;
}
