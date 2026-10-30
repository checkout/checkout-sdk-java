package com.checkout.handlepaymentsandpayouts.setups.responses;

import com.checkout.common.Resource;
import com.checkout.common.Currency;
import com.checkout.handlepaymentsandpayouts.setups.entities.accountFundingTransaction.PaymentSetupAccountFundingTransaction;
import com.checkout.handlepaymentsandpayouts.setups.entities.billing.PaymentSetupBilling;
import com.checkout.handlepaymentsandpayouts.setups.entities.billingDescriptor.PaymentSetupBillingDescriptor;
import com.checkout.handlepaymentsandpayouts.setups.entities.customer.Customer;
import com.checkout.handlepaymentsandpayouts.setups.entities.industry.Industry;
import com.checkout.handlepaymentsandpayouts.setups.entities.order.Order;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.PaymentMethods;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentType.PaymentSetupPaymentType;
import com.checkout.handlepaymentsandpayouts.setups.entities.presentmentDetails.PaymentSetupPresentmentDetails;
import com.checkout.handlepaymentsandpayouts.setups.entities.risk.PaymentSetupRisk;
import com.checkout.handlepaymentsandpayouts.setups.entities.settings.Settings;
import com.checkout.handlepaymentsandpayouts.setups.entities.terminal.PaymentSetupTerminal;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Payment setup response
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentSetupsResponse extends Resource {

    /**
     * The unique identifier of the payment setup.
     * [Optional] readOnly
     */
    private String id;

    /**
     * The processing channel to use for the payment.
     * [Required]
     * Pattern: ^(pc)_(\w{26})$
     */
    private String processingChannelId;

    /**
     * The payment amount, in the minor currency unit.
     * [Required]
     */
    private Long amount;

    /**
     * The currency of the payment, as a three-letter ISO currency code.
     * [Required]
     */
    private Currency currency;

    /**
     * The type of payment. You must provide this field for card payments in which the cardholder
     * is not present. For example, if the transaction is a recurring payment, or a mail
     * order/telephone order (MOTO) payment.
     * [Optional]
     * Default: regular
     */
    private PaymentSetupPaymentType paymentType;

    /**
     * A reference you can use to identify the payment. For example, an order number.
     * [Optional]
     * max 80 characters
     */
    private String reference;

    /**
     * A description of the payment.
     * [Optional]
     * max 100 characters
     */
    private String description;

    /**
     * The payment methods that are enabled on your account and available for use.
     * [Optional]
     */
    private PaymentMethods paymentMethods;

    /**
     * Settings for the Payment Setup.
     * [Optional]
     */
    private Settings settings;

    /**
     * The customer's details.
     * [Optional]
     */
    private Customer customer;

    /**
     * The customer's order details.
     * [Optional]
     */
    private Order order;

    /**
     * Industry-specific information.
     * [Optional]
     */
    private Industry industry;

    /**
     * The billing details for the payment.
     * [Optional]
     */
    private PaymentSetupBilling billing;

    /**
     * An ordered list of available payment method names. The order indicates the recommended
     * presentation priority, with the first item being the highest priority.
     * [Optional] readOnly
     */
    private List<String> availablePaymentMethods;

    /**
     * Account funding transaction details for the payment.
     * [Optional]
     */
    private PaymentSetupAccountFundingTransaction accountFundingTransaction;

    /**
     * The billing descriptor for payment.
     * [Optional]
     */
    private PaymentSetupBillingDescriptor billingDescriptor;

    /**
     * The latest payment for this setup. Returned after a successful confirmation, including
     * auto-confirm during setup creation. For the full response schema, see Get payment details.
     * [Optional] readOnly
     */
    private Object latestPayment;

    /**
     * The amount and currency to present to the customer, when the settlement currency differs from the
     * customer-facing currency.
     * [Optional]
     */
    private PaymentSetupPresentmentDetails presentmentDetails;

    /**
     * Terminal details.
     * [Optional]
     */
    private PaymentSetupTerminal terminal;

    /**
     * Risk assessment settings for the Payment Setup.
     * [Optional]
     */
    private PaymentSetupRisk risk;

    /**
     * A set of key/value pairs you can attach to a payment. It can be useful for storing additional
     * information about the payment.
     * Each value is a string (max 255 characters), a number or a boolean.
     * [Optional]
     * max 20 properties
     */
    private Map<String, Object> metadata;
}
