package com.checkout.payments.contexts;

import com.checkout.payments.AccommodationData;
import com.checkout.payments.BillingPlan;
import com.checkout.payments.ShippingPreference;
import com.checkout.payments.UserAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Settings that control how the payment context is processed.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentContextsProcessing {

    /**
     * The plan details for a recurring payment with PayPal.
     * Required when {@code payment_type} is {@code recurring}.
     * [Optional]
     */
    private BillingPlan plan;

    /**
     * The discount amount the merchant applied to the transaction.
     * [Optional]
     */
    private Integer discountAmount;

    /**
     * The total freight or shipping and handling charges for the transaction.
     * [Optional]
     */
    private Integer shippingAmount;

    /**
     * The total tax amount for the transaction, in the minor currency unit.
     * [Optional]
     */
    private Integer taxAmount;

    /**
     * Invoice ID number.
     * [Optional]
     */
    private String invoiceId;

    /**
     * The label that overrides the business name in the PayPal account on the PayPal pages.
     * [Optional]
     */
    private String brandName;

    /**
     * The language and region of the customer in ISO 639-2 language code; the value consists of
     * language-country.
     * [Optional]
     */
    private String locale;

    /**
     * Shipping preference.
     * [Optional]
     * One of: no_shipping, set_provided_address, get_from_file
     */
    private ShippingPreference shippingPreference;

    /**
     * Property required by PayPal to have an appropriate payment flow.
     * [Optional]
     * One of: pay_now, continue
     */
    private UserAction userAction;

    /**
     * Key-and-value pairs with merchant-specific data for the transaction.
     * [Optional]
     */
    private List<PaymentContextsPartnerCustomerRiskData> partnerCustomerRiskData;

    /**
     * Promo codes. Defines which of the configured payment options within a payment category
     * (pay_later, pay_over_time, and so on) are shown for this purchase.
     * [Optional]
     */
    private List<String> customPaymentMethodIds;

    /**
     * Contains information about the airline ticket and flights booked by the customer.
     * [Optional]
     */
    private List<PaymentContextsAirlineData> airlineData;

    /**
     * Contains information about the accommodation booked by the customer.
     * [Optional]
     * <p>
     * Uses the shared {@link AccommodationData}, because payment contexts, {@code POST /payments}
     * and the {@code GET /payments/{id}} response all resolve {@code accommodation_data} to the
     * same specification schema. It was previously a separate
     * {@link PaymentContextsAccommodationData}, which had already drifted from its twin.
     */
    private List<AccommodationData> accommodationData;

}
