package com.checkout.payments.links;

import com.checkout.common.Product;
import com.checkout.payments.BillingInformation;
import com.checkout.payments.CustomerRequest;
import com.checkout.payments.RiskRequest;
import com.checkout.payments.ThreeDSRequest;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@Data
@Builder
public final class PaymentLinkRequest {
    @NotEmpty
    private Long amount;
    @NotEmpty
    private String currency;
    private String reference;
    private String description;
    private Integer expiresIn;
    private CustomerRequest customer;
    @NotEmpty
    private BillingInformation billing;
    private List<Product> products;
    private Map<String, Object> metadata;
    @SerializedName("3ds")
    private ThreeDSRequest threeDS;
    private RiskRequest risk;
    private String returnUrl;
    private String locale;
}
