package com.checkout.payments.contexts;

import com.checkout.payments.BillingPlan;
import com.checkout.payments.ShippingPreference;
import com.checkout.payments.UserAction;
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

    private Integer discountAmount;

    private Integer shippingAmount;

    private Integer taxAmount;

    private String invoiceId;

    private String brandName;

    private String locale;

    private ShippingPreference shippingPreference;

    private UserAction userAction;

    private List<PaymentContextsPartnerCustomerRiskData> partnerCustomerRiskData;

    private List<String> customPaymentMethodIds;

    private List<PaymentContextsAirlineData> airlineData;

    private List<PaymentContextsAccommodationData> accommodationData;

}
