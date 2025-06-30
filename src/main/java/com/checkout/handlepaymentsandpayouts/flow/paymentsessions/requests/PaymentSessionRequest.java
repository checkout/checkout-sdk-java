package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests;

import com.checkout.common.Currency;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.DisabledPaymentMethodsType;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.EnabledPaymentMethodsType;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.LocaleType;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.PaymentType;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.sender.AbstractSender;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentSessionRequest {

    /**
     * The payment amount. Provide a value of 0 to perform a card verification. The amount must be provided in the minor
     * currency unit.
     */
    private Long amount;

    /**
     * The three-letter ISO currency code
     */
    private Currency currency;

    /**
     * The billing details.
     */
    private Billing billing;

    /**
     * Overrides the default success redirect URL configured on your account, for payment methods that require a
     * redirect.
     */
    @SerializedName("success_url")
    private String successUrl;

    /**
     * Overrides the default failure redirect URL configured on your account, for payment methods that require a
     * redirect.
     */
    @SerializedName("failure_url")
    private String failureUrl;

    /**
     * Default: "Regular"
     * Must be specified for card-not-present (CNP) payments. For example, a recurring mail order / telephone order
     * (MOTO) payment.
     */
    @Builder.Default
    @SerializedName("payment_type")
    private PaymentType paymentType = PaymentType.REGULAR;

    /**
     * A description of the purchase, which is displayed on the customer's statement.
     */
    @SerializedName("billing_descriptor")
    private BillingDescriptor billingDescriptor;

    /**
     * A reference you can use to identify the payment. For example, an order number. For Amex payments, this must be at
     * most 30 characters. For Benefit payments, the reference must be a unique alphanumeric value. For iDEAL payments,
     * the reference is required and must be an alphanumeric value with a 35-character limit.
     */
    private String reference;

    /**
     * A description for the payment.
     */
    private String description;

    /**
     * The customer's details. Required if source.type is tamara.
     */
    private Customer customer;

    /**
     * The shipping details
     */
    private Shipping shipping;

    /**
     * Information about the recipient of the payment's funds. Applies to Account Funding Transactions, and VISA or
     * Mastercard domestic UK transactions processed by financial institutions.
     */
    private Recipient recipient;

    /**
     * Use the processing object to influence or override the data sent during card processing
     */
    private Processing processing;

    /**
     * Details about the payment instruction.
     */
    private Instruction instruction;

    /**
     * The processing channel to use for the payment.
     */
    @SerializedName("processing_channel_id")
    private String processingChannelId;

    /**
     * Configurations for payment method-specific settings.
     */
    @SerializedName("payment_method_configuration")
    private PaymentMethodConfiguration paymentMethodConfiguration;

    /**
     * The line items in the order.
     */
    private List<Item> items;

    /**
     * The sub-entities that the payment is being processed on behalf of.
     */
    @SerializedName("amount_allocations")
    private List<AmountAllocation> amountAllocations;

    /**
     * Configures the risk assessment performed during payment processing.
     */
    private Risk risk;

    /**
     * The merchant's display name.
     */
    @SerializedName("display_name")
    private String displayName;

    /**
     * Allows you to store additional information about a transaction with custom fields and up to five user-defined
     * fields, which can be used for reporting purposes. You can supply fields of type string, number, and boolean
     * within the metadata object. Arrays and objects are not supported.
     * You can provide up to 18 metadata fields per API call, but the value of each field must not exceed 255 characters
     * in length. You can also reference metadata properties in your custom rules for Fraud Detection. For example,
     * $coupon_code = '1234’.
     */
    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();

    /**
     * Default: "en-GB" Creates a translated version of the page in the specified language.
     */
    @Builder.Default
    private LocaleType locale = LocaleType.EN_GB;

    /**
     * Information required for 3D Secure authentication payments.
     */
    @SerializedName("3ds")
    private Threeds threeds;

    /**
     * The sender of the payment.
     */
    private AbstractSender sender;

    /**
     * Default: true
     * Specifies whether to capture the payment, if applicable.
     */
    @Builder.Default
    private Boolean capture = true;

    /**
     * A timestamp specifying when to capture the payment, as an ISO 8601 code. If a value is provided, capture is
     * automatically set to true.
     */
    @SerializedName("capture_on")
    private Instant captureOn;

    /**
     * A timestamp specifying when the PaymentSession should expire, as an ISO 8601 code. If no value is provided, expiry is
     * set to 1 hour after the PaymentSession is created. You cannot set the session expiry to more than 60 days after the
     * PaymentSession is created.
     */
    @SerializedName("expires_on")
    private Instant expiresOn;

    /**
     * Specifies which payment method options to present to the customer. The values in this field override any equivalent
     * values in disabled_payment_methods.
     */
    @SerializedName("enabled_payment_methods")
    private List<EnabledPaymentMethodsType> enabledPaymentMethods;

    /**
     * Specifies which payment method options to not present to the customer. If you specify the same payment method in this
     * field and in enabled_payment_methods, the disabled_payment_methods value will be overridden. Any payment method
     * options not explicitly specified in this field will be presented to the customer by default.
     */
    @SerializedName("disabled_payment_methods")
    private List<DisabledPaymentMethodsType> disabledPaymentMethods;

    /**
     * Configuration for asynchronous retries.
     */
    @SerializedName("customer_retry")
    private CustomerRetry customerRetry;

    /**
     * @deprecated This property will be removed in the future, and should be used with caution.
     * The Customers IP address. Only IPv4 and IPv6 addresses are accepted.
     */
    @SerializedName("ip_address")
    private String ipAddress;

}
