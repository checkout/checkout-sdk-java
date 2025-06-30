package com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests;

import com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.enums.MerchantInitiatedReasonType;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.enums.PanPreferenceType;
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
public final class Processing {

    /**
     * Indicates whether the payment is an Account Funding Transaction
     */
    private Boolean aft;

    /**
     * The discount amount applied to the transaction by the merchant.
     */
    @SerializedName("discount_amount")
    private Double discountAmount;

    /**
     * The total freight or shipping and handling charges for the transaction.
     */
    @SerializedName("shipping_amount")
    private Double shippingAmount;

    /**
     * The customer's value-added tax registration number.
     */
    @SerializedName("tax_amount")
    private Double taxAmount;

    /**
     * Invoice ID number.
     */
    @SerializedName("invoice_id")
    private String invoiceId;

    /**
     * The label that overrides the business name in the PayPal account on the PayPal pages.
     */
    @SerializedName("brand_name")
    private String brandName;

    /**
     * The language and region of the customer in ISO 639-2 language code; value consists of language-country.
     */
    private String locale;

    /**
     * An array of key-and-value pairs with merchant-specific data for the transaction.
     */
    @SerializedName("partner_customer_risk_data")
    private List<PartnerCustomerRiskData> partnerCustomerRiskData;

    /**
     * Promo codes - An array that can be used to define which of the configured payment options within a payment
     * category (pay_later, pay_over_time, etc.) should be shown for this purchase.
     */
    @SerializedName("custom_payment_method_ids")
    private List<String> customPaymentMethodIds;

    /**
     * Contains information about the airline ticket and flights booked by the customer.
     */
    @SerializedName("airline_data")
    private List<AirlineData> airlineData;

    /**
     * Contains information about the accommodation booked by the customer.
     */
    @SerializedName("accommodation_data")
    private List<AccommodationData> accommodationData;

    /**
     * The number provided by the cardholder. Purchase order or invoice number may be used.
     */
    @SerializedName("order_id")
    private String orderId;

    /**
     * Surcharge amount applied to the transaction in minor units by the merchant.
     */
    @SerializedName("surcharge_amount")
    private Long surchargeAmount;

    /**
     * The total charges for any import/export duty included in the transaction.
     */
    @SerializedName("duty_amount")
    private Double dutyAmount;

    /**
     * The tax amount of the freight or shipping and handling charges for the transaction.
     */
    @SerializedName("shipping_tax_amount")
    private Double shippingTaxAmount;

    /**
     * The purchase country of the customer. ISO 3166 alpha-2 purchase country.
     */
    @SerializedName("purchase_country")
    private String purchaseCountry;

    /**
     * Indicates the reason for a merchant-initiated payment request.
     */
    @SerializedName("merchant_initiated_reason")
    private MerchantInitiatedReasonType merchantInitiatedReason;

    /**
     * Unique number of the campaign this payment will be running in. Only required for Afterpay campaign invoices.
     */
    @SerializedName("campaign_id")
    private Long campaignId;

    /**
     * The payment for a merchant's order may be split, and the original order price indicates the transaction amount of the
     * entire order.
     */
    @SerializedName("original_order_amount")
    private Double originalOrderAmount;

    /**
     * Merchant receipt ID.
     */
    @SerializedName("receipt_id")
    private String receiptId;

    /**
     * A URL which you can use to notify the customer that the order has been created.
     */
    @SerializedName("merchant_callback_url")
    private String merchantCallbackUrl;

    /**
     * Beta
     * The line of business that the payment is associated with.
     */
    @SerializedName("line_of_business")
    private String lineOfBusiness;

    /**
     * Specifies the preferred type of Primary Account Number (PAN) for the payment:
     * DPAN: Uses the Checkout.com Network Token.
     * FPAN: Uses the full card number.
     * Note: This only works when source.type is any of: cards instruments tokens
     */
    @SerializedName("pan_preference")
    private PanPreferenceType panPreference;

    /**
     * Default: true
     * Indicates whether to provision a network token for the payment.
     */
    @Builder.Default
    @SerializedName("provision_network_token")
    private Boolean provisionNetworkToken = true;

}
