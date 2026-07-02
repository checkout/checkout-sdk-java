package com.checkout.payments.links;

import com.checkout.common.AmountAllocations;
import com.checkout.common.Currency;
import com.checkout.common.CustomerResponse;
import com.checkout.common.Product;
import com.checkout.common.Resource;
import com.checkout.payments.BillingInformation;
import com.checkout.payments.ShippingDetails;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public final class PaymentLinkDetailsResponse extends Resource {

    private String id;

    private PaymentLinkStatus status;

    private String paymentId;

    private Long amount;

    private Currency currency;

    private String reference;

    private String description;

    private String createdOn;

    private String expiresOn;

    private String processingChannelId;

    private List<AmountAllocations> amountAllocations;

    private CustomerResponse customer;

    private ShippingDetails shipping;

    private BillingInformation billing;

    private List<Product> products;

    private Map<String, Object> metadata;

    private String locale;

    private String returnUrl;

}
