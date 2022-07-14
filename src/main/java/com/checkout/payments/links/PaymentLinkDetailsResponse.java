package com.checkout.payments.links;

import com.checkout.common.Currency;
import com.checkout.common.CustomerResponse;
import com.checkout.common.MarketplaceData;
import com.checkout.common.Resource;
import com.checkout.common.Product;
import com.checkout.payments.BillingInformation;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public final class PaymentLinkDetailsResponse extends Resource {

    private String id;

    private PaymentLinkStatus status;

    @SerializedName("expires_on")
    private String expiresOn;

    @SerializedName("return_url")
    private String returnUrl;

    private Long amount;

    private Currency currency;

    private String reference;

    private String description;

    private CustomerResponse customer;

    private BillingInformation billing;

    private List<Product> products;

    private Map<String, Object> metadata;

    /**
     * Not available on Previous
     */

    @SerializedName("processing_channel_id")
    private String processingChannelId;

    private MarketplaceData marketplace;

}
