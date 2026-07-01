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
import com.checkout.handlepaymentsandpayouts.setups.entities.presentmentDetails.PaymentSetupPresentmentDetails;
import com.checkout.handlepaymentsandpayouts.setups.entities.settings.Settings;
import com.checkout.handlepaymentsandpayouts.setups.entities.terminal.PaymentSetupTerminal;
import com.checkout.payments.PaymentType;

import java.util.List;
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
     * [Optional]
     */
    private String id;

    /**
     * The processing channel used for the payment setup.
     * [Required]
     * Pattern: ^(pc)_(\w{26})$
     */
    private String processingChannelId;

    /**
     * The payment amount in the minor currency unit.
     * [Required]
     */
    private Long amount;

    /**
     * The three-letter ISO currency code.
     * [Required]
     */
    private Currency currency;

    /**
     * The type of payment.
     * [Optional]
     * Enum: "Regular" "Recurring" "MOTO" "Installment" "PayLater" "Unscheduled"
     */
    private PaymentType paymentType;

    /**
     * A reference you can use to identify the payment setup, such as an order number.
     * [Optional]
     */
    private String reference;

    /**
     * A description of the payment setup.
     * [Optional]
     */
    private String description;

    /**
     * The payment method configuration for this setup.
     * [Optional]
     */
    private PaymentMethods paymentMethods;

    /**
     * The payment setup configuration settings.
     * [Optional]
     */
    private Settings settings;

    /**
     * Details about the customer.
     * [Optional]
     */
    private Customer customer;

    /**
     * Details about the order associated with this payment setup.
     * [Optional]
     */
    private Order order;

    /**
     * Industry-specific information for specialized payment scenarios.
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
     * [Optional]
     */
    private List<String> availablePaymentMethods;

    /**
     * Account funding transaction details for the payment.
     * [Optional]
     */
    private PaymentSetupAccountFundingTransaction accountFundingTransaction;

    /**
     * The billing descriptor for the payment.
     * [Optional]
     */
    private PaymentSetupBillingDescriptor billingDescriptor;

    /**
     * The latest payment response from the gateway, populated when auto-confirm succeeds during setup
     * creation. For the full response schema, see Request a Payment or Payout.
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
}
