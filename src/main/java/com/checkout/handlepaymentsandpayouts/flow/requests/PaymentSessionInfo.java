package com.checkout.handlepaymentsandpayouts.flow.requests;

import com.checkout.common.Currency;
import com.checkout.handlepaymentsandpayouts.flow.entities.Customer;
import com.checkout.payments.BillingInformation;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.BillingDescriptor;
import com.checkout.payments.PaymentRecipient;
import com.checkout.payments.ProcessingSettings;
import com.checkout.payments.PaymentInstruction;
import com.checkout.common.AmountAllocations;
import com.checkout.payments.RiskRequest;
import com.checkout.payments.sender.PaymentSender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Extended base class for payment session requests that include full payment details
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class PaymentSessionInfo extends PaymentSessionBase {

    /**
     * The three-letter ISO currency code
     */
    private Currency currency;

    /**
     * The billing details.
     */
    private BillingInformation billing;

    /**
     * Overrides the default success redirect URL configured on your account, 
     * for payment methods that require a redirect.
     */
    private String successUrl;

    /**
     * Overrides the default failure redirect URL configured on your account, 
     * for payment methods that require a redirect.
     */
    private String failureUrl;

    /**
     * A description of the purchase, which is displayed on the customer's statement.
     */
    private BillingDescriptor billingDescriptor;

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
    private ShippingDetails shipping;

    /**
     * Information about the recipient of the payment's funds.
     */
    private PaymentRecipient recipient;

    /**
     * Use the processing object to influence or override the data sent during card processing
     */
    private ProcessingSettings processing;

    /**
     * Details about the payment instruction.
     */
    private PaymentInstruction instruction;

    /**
     * The processing channel to use for the payment.
     */
    private String processingChannelId;

    /**
     * The sub-entities that the payment is being processed on behalf of.
     */
    private List<AmountAllocations> amountAllocations;

    /**
     * Configures the risk assessment performed during payment processing.
     */
    private RiskRequest risk;

    /**
     * The merchant's display name.
     */
    private String displayName;

    /**
     * Allows you to store additional information about a transaction with custom fields.
     */
    private Map<String, Object> metadata;

    /**
     * The sender of the payment.
     */
    private PaymentSender sender;

    /**
     * Specifies whether to capture the payment, if applicable. Default: true
     */
    @Builder.Default
    private Boolean capture = true;

    /**
     * A timestamp specifying when to capture the payment, as an ISO 8601 code.
     * If a value is provided, capture is automatically set to true.
     */
    private Instant captureOn;
}