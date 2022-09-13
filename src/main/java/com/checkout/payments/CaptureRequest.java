package com.checkout.payments;

import com.checkout.common.AmountAllocations;
import com.checkout.common.MarketplaceData;
import com.checkout.payments.request.PaymentCustomerRequest;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
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

    /**
     * @deprecated This property will be removed in the future, and should be used
     * {@link CaptureRequest#amountAllocations} instead
     */
    @Deprecated
    private MarketplaceData marketplace;

    @SerializedName("amount_allocations")
    private List<AmountAllocations> amountAllocations;

    private ProcessingSettings processing;

    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();

}