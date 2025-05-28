package com.checkout.payments.contexts;

import com.checkout.payments.BillingPlan;
import com.checkout.payments.ShippingPreference;
import com.checkout.payments.UserAction;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentContextsProcessing {

    private BillingPlan plan;

    @SerializedName("discount_amount")
    private Integer discountAmount;

    @SerializedName("shipping_amount")
    private Integer shippingAmount;

    @SerializedName("tax_amount")
    private Integer taxAmount;

    @SerializedName("invoice_id")
    private String invoiceId;

    @SerializedName("brand_name")
    private String brandName;

    private String locale;

    @SerializedName("shipping_preference")
    private ShippingPreference shippingPreference;

    @SerializedName("user_action")
    private UserAction userAction;

    @SerializedName("partner_customer_risk_data")
    private List<PaymentContextsPartnerCustomerRiskData> partnerCustomerRiskData;

    @SerializedName("custom_payment_method_ids")
    private List<String> customPaymentMethodIds;

    @SerializedName("airline_data")
    private List<PaymentContextsAirlineData> airlineData;

    @SerializedName("accommodation_data")
    private List<PaymentContextsAccommodationData> accommodationData;

}
