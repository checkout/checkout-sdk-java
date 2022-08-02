package com.checkout.payments.four;

import com.checkout.common.MarketplaceData;
import com.checkout.common.four.Product;
import com.checkout.payments.BillingDescriptor;
import com.checkout.payments.ProcessingSettings;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.four.request.PaymentCustomerRequest;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public final class CaptureRequest {

    private Long amount;

    @SerializedName("capture_type")
    private CaptureType captureType;

    private String reference;

    private PaymentCustomerRequest customer;

    private String description;

    @SerializedName("billing_descriptor")
    private BillingDescriptor billingDescriptor;

    private ShippingDetails shipping;

    private List<Product> items;

    private MarketplaceData marketplace;

    private ProcessingSettings processing;

    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();

}
