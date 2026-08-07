package com.checkout.payments.response;

import com.checkout.common.CountryCode;
import com.checkout.payments.AccommodationData;
import com.checkout.payments.AirlineData;
import com.checkout.payments.PanProcessedType;
import com.checkout.payments.PreferredSchema;
import lombok.Data;

import java.util.List;

@Data
public final class ProcessingData {

    /**
     * The preferred scheme for co-badged card payment processing. If performing 3DS via a third party,
     * this is the scheme that processed 3DS. Does not support PINless debit schemes in the US
     * (STAR, PULSE, NYCE, ACCEL, SHAZAM).
     * [Optional]
     */
    private PreferredSchema preferredScheme;

    /**
     * The customer's application identifier.
     * [Optional]
     */
    private String appId;

    /**
     * The customer's ID on the partner platform.
     * [Optional]
     */
    private String partnerCustomerId;

    /**
     * The partner-originated unique payment identifier.
     * [Optional]
     */
    private String partnerPaymentId;

    /**
     * Total tax amount of the order.
     * [Optional]
     */
    private Long taxAmount;

    /**
     * The country where the purchase was made. ISO 3166-1 alpha-2 country code.
     * Not documented in the public spec for this response, kept for backward compatibility.
     * [Optional]
     */
    private CountryCode purchaseCountry;

    /**
     * The language and region of the customer. ISO 639-2 language code, its value consists of
     * language-country.
     * [Optional]
     * Pattern: ^[a-z]{2}(?:-[A-Z][a-z]{3})?(?:-(?:[A-Z]{2}))?$
     * min 2 characters, max 10 characters
     */
    private String locale;

    /**
     * A unique identifier for the authorization provided by partner.
     * [Optional]
     */
    private String retrievalReferenceNumber;

    /**
     * The Klarna order ID associated with the payment.
     * [Optional]
     */
    private String partnerOrderId;

    /**
     * Status of a payment provided by partner.
     * [Optional]
     */
    private String partnerStatus;

    /**
     * Unique transaction identification provided by partner.
     * [Optional]
     */
    private String partnerTransactionId;

    /**
     * The list of error codes that led the payment to fail or be declined, as given by the
     * payment provider.
     * [Optional]
     */
    private List<String> partnerErrorCodes;

    /**
     * Error description provided by partner.
     * [Optional]
     */
    private String partnerErrorMessage;

    /**
     * Authorization code provided by partner.
     * [Optional]
     */
    private String partnerAuthorizationCode;

    /**
     * Authorization response code provided by partner.
     * [Optional]
     */
    private String partnerAuthorizationResponseCode;

    /**
     * Fraud status of the payment.
     * Not documented in the public spec for this response, kept for backward compatibility.
     * Prefer {@code partnerFraudStatus}.
     * [Optional]
     */
    private String fraudStatus;

    /**
     * The payment method authorized by the provider.
     * Not documented in the public spec for this response, kept for backward compatibility.
     * [Optional]
     */
    private ProviderAuthorizedPaymentMethod providerAuthorizedPaymentMethod;

    /**
     * An array defining which of the configured payment options within a payment category
     * (for example, {@code pay_later} or {@code pay_over_time}) should be displayed for this purchase.
     * [Optional]
     */
    private List<String> customPaymentMethodIds;

    /**
     * Indicates whether the payment is an Account Funding Transaction.
     * [Optional]
     */
    private Boolean aft;

    /**
     * Four-digit code for retail financial services expressed in ISO 18245 format, classifying
     * the types of goods or services you provide.
     * [Optional]
     */
    private String merchantCategoryCode;

    /**
     * The merchant identifier that was configured with the scheme and used for the payment.
     * [Optional]
     */
    private String schemeMerchantId;

    /**
     * The type of Primary Account Number (PAN) used for the payment. DPAN indicates a network
     * token was used, FPAN indicates the full card was used.
     * [Optional]
     * Enum: "fpan" "dpan"
     */
    private PanProcessedType panTypeProcessed;

    /**
     * Indicates whether a Checkout.com Network Token was available for the payment.
     * Not documented in the public spec for this response, kept for backward compatibility.
     * [Optional]
     */
    private Boolean ckoNetworkTokenAvailable;

    /**
     * Indicates whether the {@code fallback_source} field was used for the payment.
     * [Optional]
     */
    private Boolean fallbackSourceUsed;

    /**
     * A high-level failure category returned by the payment provider when a payment is declined or fails.
     * Not all payment methods return this field.
     * [Optional]
     */
    private String failureCode;

    /**
     * The 6-digit partner code returned by the payment provider. Returned when {@code source.type} is {@code blik}.
     * [Optional]
     * Pattern: ^\d{6}$
     * 6 characters
     */
    private String partnerCode;

    /**
     * The raw response code returned by the payment provider when a payment is declined or fails.
     * Not all payment methods return this field.
     * [Optional]
     */
    private String partnerResponseCode;

    /**
     * The scheme on which the payment was authorized. This may differ from the card's scheme used
     * for the payment if the card is co-badged and the payment was authorized on a different network.
     * [Optional] readOnly
     */
    private String scheme;

    /**
     * Partner fraud status. If the status is {@code Pending}, and the merchant captures before it
     * changes to {@code Accepted}, the risk of the transaction is solely on the merchant.
     * [Optional]
     */
    private String partnerFraudStatus;

    /**
     * The Mastercard Merchant Advice Code (MAC), which contains additional information about the
     * transaction. For example, the MAC can inform you if the transaction was performed using a
     * consumer non-reloadable prepaid card or a consumer single-use virtual card. For declined
     * transactions, the MAC also indicates whether the payment can be retried and how long to wait.
     * [Optional]
     */
    private String partnerMerchantAdviceCode;

    /**
     * Contains information about the accommodation booked by the customer.
     * [Optional]
     */
    private List<AccommodationData> accommodationData;

    /**
     * Contains information about the airline ticket and flights booked by the customer.
     * [Optional]
     */
    private List<AirlineData> airlineData;

    /**
     * The scheme transaction link identifier. Returned for Mastercard transactions when the scheme
     * provides a link identifier that ties together related transactions on the network
     * (see Mastercard Transaction Link Identifier documentation).
     * [Optional]
     */
    private String schemeTransactionLinkId;

}
