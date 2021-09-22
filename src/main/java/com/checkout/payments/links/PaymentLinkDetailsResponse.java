package com.checkout.payments.links;

import com.checkout.common.Currency;
import com.checkout.common.CustomerRequest;
import com.checkout.common.Product;
import com.checkout.common.Resource;
import com.checkout.payments.BillingInformation;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public final class PaymentLinkDetailsResponse extends Resource {

    private String id;

    private String status;

    @SerializedName("expires_on")
    private String expiresOn;

    @SerializedName("return_url")
    private String returnUrl;

    private Long amount;

    private Currency currency;

    private String reference;

    private String description;

    private CustomerRequest customer;

    private BillingInformation billing;

    private List<Product> products;

}
