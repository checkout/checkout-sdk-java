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
import com.checkout.payments.sender.PaymentSender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Extended base class for payment session requests that include full payment details.
 *
 * This class holds only the fields that every payment session request accepts, whether it
 * creates a session or submits a payment attempt for an existing one. Fields that only the
 * session creation endpoints accept live in PaymentSessionCreateBase.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class PaymentSessionInfo extends PaymentSessionBase {

    /**
     * The three-letter ISO currency code.
     * [Optional]
     * min 3 max 3
     */
    private Currency currency;

    /**
     * The billing details.
     * [Optional]
     */
    private BillingInformation billing;

    /**
     * Overrides the default success redirect URL configured on your account,
     * for payment methods that require a redirect.
     * [Optional]
     * Format: uri
     */
    private String successUrl;

    /**
     * Overrides the default failure redirect URL configured on your account,
     * for payment methods that require a redirect.
     * [Optional]
     * Format: uri
     */
    private String failureUrl;

    /**
     * A description of the purchase, which is displayed on the customer's statement.
     * [Optional]
     */
    private BillingDescriptor billingDescriptor;

    /**
     * The customer's details. Required if source.type is tamara.
     * [Optional]
     */
    private Customer customer;

    /**
     * The shipping details.
     * [Optional]
     */
    private ShippingDetails shipping;

    /**
     * Information about the recipient of the payment's funds.
     * [Optional]
     */
    private PaymentRecipient recipient;

    /**
     * Use the processing object to influence or override the data sent during card processing.
     * [Optional]
     */
    private ProcessingSettings processing;

    /**
     * Details about the payment instruction.
     * [Optional]
     */
    private PaymentInstruction instruction;

    /**
     * The processing channel to use for the payment.
     * [Optional]
     * Pattern: ^(pc)_(\w{26})$
     */
    private String processingChannelId;

    /**
     * The sub-entities that the payment is being processed on behalf of.
     * [Optional]
     * min 1 max 50 items
     */
    private List<AmountAllocations> amountAllocations;

    /**
     * Allows you to store additional information about a transaction with custom fields.
     * [Optional]
     */
    private Map<String, Object> metadata;

    /**
     * The sender of the payment.
     * [Optional]
     */
    private PaymentSender sender;

    /**
     * A timestamp specifying when to capture the payment, as an ISO 8601 code.
     * If a value is provided, capture is automatically set to true by the API.
     * [Optional]
     * Format: date-time (ISO 8601)
     */
    private Instant captureOn;
}
